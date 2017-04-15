package com.mill.controller;

import static com.mill.utils.Constants.*;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mill.exceptions.WSException;
import com.mill.model.Images;
import com.mill.model.ProductImages;
import com.mill.model.ProductTags;
import com.mill.model.Products;
import com.mill.model.Purchases;
import com.mill.model.Recomendations;
import com.mill.model.Stores;
import com.mill.model.Tags;
import com.mill.model.Tastes;
import com.mill.model.Users;
import com.mill.session.ImagesFacade;
import com.mill.session.ProductsFacade;
import com.mill.session.PurchasesFacade;
import com.mill.session.RecomendationsFacade;
import com.mill.session.StoresFacade;
import com.mill.session.TagsFacade;
import com.mill.session.TastesFacade;
import com.mill.session.UsersFacade;
import com.mill.utils.Message;
import com.mill.utils.Result;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;

public class ProductManager {

    private final ObjectMapper mapper;
    private final ProductsFacade productsFacade = lookupProductsFacadeBean();
    private final TagsFacade tagsFacade = lookupTagsFacadeBean();
    private final ImagesFacade imagesFacade = lookupImagesFacadeBean();
    private final UsersFacade usersFacade = lookupUsersFacadeBean();
    private final TastesFacade tastesFacade = lookupTastesFacadeBean();
    private final RecomendationsFacade recomendationsFacade = lookupRecomendationsFacadeBean();
    private final StoresFacade storesFacade = lookupStoresFacadeBean();
    private final PurchasesFacade purchasesFacade = lookupPurchasesFacadeBean();

    public ProductManager(ObjectMapper mapper)
    {
        this.mapper = mapper;
    }

    public Result process(Message message, String username) throws SQLException, WSException, NamingException
    {
        try
        {
            switch (message.getOperation())
            {
                case OPER_SHOW_RAND_PRODS:
                    return showRandomProducts(message.getData(), username);
                case OPER_LIKE_PRODUCT:
                    return addTaste(message.getData(), username, true);
                case OPER_REJECT_PRODUCT:
                    return addTaste(message.getData(), username, false);
                case OPER_RECOMEND_PRODS:
                    return recomendProduct(message.getData(), username);
                case OPER_SHOW_LIKED:
                    return showUserProds(message.getData(), username, true);
                case OPER_SHOW_REJECTED:
                    return showUserProds(message.getData(), username, false);
                case OPER_SHOW_RECOMENDATIONS:
                    return showRecomendations(message.getData(), username);
                case OPER_ADD_PRODUCT:
                    return addProduct(message.getData(), username);
                case OPER_SHOW_PRODUCT:
                    return setProductVisibility(message.getData(), username, true);
                case OPER_HIDE_PRODUCT:
                    return setProductVisibility(message.getData(), username, false);
                case OPER_UPDATE_PRODUCT:
                    return updateProduct(message.getData(), username);
                case OPER_ADD_PRODUCT_TAG:
                    return addTag(message.getData(), username);
                case OPER_ADD_PRODUCT_IMAGE:
                    return addImage(message.getData(), username);
                case OPER_ADD_PURCHASE:
                    return addPurchase(message.getData(), username);
                case OPER_SHOW_PURCHASES:
                    return showPurchases(message.getData(), username);
                default:
                    throw new WSException(INVALID_OPERATION, "Operación no válida");
            }
        } catch (JsonParseException e)
        {
            throw new WSException(JSON_ERROR, "Error transformando JSON");
        } catch (JsonMappingException e)
        {
            e.printStackTrace();
            throw new WSException(JSON_ERROR, "Error en mapeo JSON");
        } catch (IOException e)
        {
            throw new WSException(JSON_ERROR, "Error de flujo de JSON");
        }
    }

    private Result addProduct(String data, String username)
            throws SQLException, JsonParseException, JsonMappingException, IOException, NamingException
    {
        Result r = new Result();

        Products product = mapper.readValue(data, Products.class);
        product.setPublishdate(new Date(System.currentTimeMillis()));
        productsFacade.create(product);

        r.setState(STATE_OK);
        r.setData("Producto agregado");

        return r;
    }

    private Result addTag(String data, String username)
            throws SQLException, JsonParseException, JsonMappingException, IOException, NamingException
    {
        Result r = new Result();
        Map<String, Object> map = mapper.readValue(data, Map.class);
        Tags tag = tagsFacade.findByName((String) map.get("name"));
        if (tag == null)
        {
            tag = new Tags();
            tag.setName((String) map.get("name"));
            tagsFacade.create(tag);
        }
        Products p = productsFacade.find((int) map.get("idproducts"));
        if (p != null)
        {
            ProductTags pt = new ProductTags();
            pt.setProductsIdproducts(p);
            pt.setTagsIdtags(tag);
            p.getProductTagsList().add(pt);
            productsFacade.edit(p);
            r.setState(STATE_OK);
            r.setData("Tag agregado");
        } else
        {
            r.setState(NO_RESULTS_ERROR);
            r.setData("Producto inexistente");
        }
        return r;
    }

    private Result addImage(String data, String username)
            throws SQLException, JsonParseException, JsonMappingException, IOException, NamingException
    {
        Result r = new Result();
        Map<String, Object> map = mapper.readValue(data, Map.class);
        System.out.println("Searching image...");
        Images image = imagesFacade.findByUrl((String) map.get("url"));

        if (image == null)
        {
            System.out.println("Image not found, creating...");
            image = new Images();
            image.setUrl((String) map.get("url"));
            imagesFacade.create(image);
        }

        System.out.println("Searching product...");
        Products p = productsFacade.find((int) map.get("idproducts"));
        if (p != null)
        {
            System.out.println("Product found, adding image...");
            ProductImages pi = new ProductImages();
            pi.setShowing((short) 1);
            pi.setImagesIdimages(image);
            pi.setProductsIdproducts(p);
            p.getProductImagesList().add(pi);
            productsFacade.edit(p);
            System.out.println("Image added...");
            r.setState(STATE_OK);
            r.setData("Imagen agregada");
        } else
        {
            System.out.println("Woopsie missed it, but you should not have");
            r.setState(STATE_OK);
            r.setData("Producto inexistente");
        }
        return r;
    }

    private Result showRandomProducts(String data, String username)
            throws SQLException, WSException, JsonProcessingException, NamingException
    {
        Result r = new Result();
        Users user = usersFacade.getUserByEmail(username);
        System.out.println("Getting liked products...");
        List<Tastes> tastes = user.getTastesList();
        List<Products> liked = new ArrayList<>();
        List<Products> result = new ArrayList<>();
        for (Tastes t : tastes)
        {
            liked.add(t.getProducts());
        }

        System.out.println("Counting products...");
        int productsNumber = productsFacade.count();
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < 20; i++)
        {
            System.out.println("Querying random product...");
            Products p = productsFacade.find(rand.nextInt(productsNumber + 1));
            if (liked.contains(p) || p.getShowing() == 0)
            {
                i--;
                System.out.println("Skipping product...");
            } else
            {
                result.add(p);
                System.out.println("Product added...");
            }
        }
        r.setState(STATE_OK);
        r.setData(mapper.writeValueAsString(result));
        return r;
    }

    private Result addTaste(String data, String username, boolean like)
            throws SQLException, JsonParseException, JsonMappingException, IOException, NamingException
    {
        Result r = new Result();
        Users user = usersFacade.getUserByEmail(username);
        Map<String, Object> map = mapper.readValue(data, Map.class);
        System.out.println("Fetching product..");
        Products p = productsFacade.find((int) map.get("product"));
        if (p != null)
        {
            System.out.println("Checking taste...");
            Tastes t = tastesFacade.findByKeys(p.getIdproducts(), user.getIdusers());
            if (t == null)
            {
                System.out.println("Taste not found, creating...");
                t = new Tastes(user.getIdusers(), p.getIdproducts());
                tastesFacade.create(t);
                t.setLiked(like);
                t.setInterDate(new Date(System.currentTimeMillis()));
                r.setData("Gusto creado");
            } else
            {
                System.out.println("Taste found updating...");
                t.setLiked(like);
                t.setInterDate(new Date(System.currentTimeMillis()));
                tastesFacade.edit(t);
                r.setData("Gusto actualizado");
            }
            r.setState(STATE_OK);
        } else
        {
            System.out.println("Product not found :(");
            r.setState(NO_RESULTS_ERROR);
            r.setData("Producto inexistente");
        }
        return r;
    }

    private Result recomendProduct(String data, String username)
            throws SQLException, JsonParseException, JsonMappingException, IOException, NamingException
    {
        Result r = new Result();
        Users user = usersFacade.getUserByEmail(username);
        Map<String, Object> map = mapper.readValue(data, Map.class);
        int recomender = user.getIdusers();
        int receipient = (int) map.get("receipient");
        int idproducts = (int) map.get("product");

        Recomendations rec = recomendationsFacade.findByKeys(recomender, receipient, idproducts);

        if (rec == null)
        {
            rec = new Recomendations(recomender, receipient, idproducts);
            rec.setShowed(Boolean.FALSE);
            rec.setRecDate(new Date(System.currentTimeMillis()));
            recomendationsFacade.create(rec);
            r.setState(STATE_OK);
            r.setData("Recomendación hecha");
        } else
        {
            r.setState(DUPLICATE_ENTRY);
            r.setData("Recomendación ya se hizo");
        }

        return r;
    }

    private Result showUserProds(String data, String username, boolean liked)
            throws SQLException, JsonProcessingException, WSException, NamingException, IOException
    {
        Result r = new Result();
        Map<String, Object> map = mapper.readValue(data, Map.class);
        System.out.println("Fetching user...");
        Users user = null;
        if(map.containsKey("user"))
        {
            user = usersFacade.find((int) map.get("user"));
        }
        else
        {
            user = usersFacade.getUserByEmail(username);
        }
        System.out.println("Fetching taste list...");
        List<Tastes> tastes = user.getTastesList();
        List<Products> prods = new ArrayList<>();

        for (Tastes t : tastes)
        {
            if (t.getLiked() == liked)
            {
                System.out.println("Adding product...");
                prods.add(t.getProducts());
            }
        }

        if (prods.isEmpty())
        {
            System.out.println("No products found...");
            r.setState(NO_RESULTS_ERROR);
            r.setData("No se encontraron productos");
        } else
        {
            System.out.println("There you go...");
            r.setState(STATE_OK);
            r.setData(mapper.writeValueAsString(prods));
        }
        return r;
    }

    private Result showRecomendations(String data, String username)
            throws SQLException, WSException, JsonProcessingException, NamingException, IOException
    {
        Result r = new Result();
        Map<String, Object> map = mapper.readValue(data, Map.class);
        System.out.println("Fetching user...");
        Users user = null;
        if(map.containsKey("user"))
        {
            user = usersFacade.find((int) map.get("user"));
        }
        else
        {
            user = usersFacade.getUserByEmail(username);
        }
        System.out.println("Fetching recomendations...");
        List<Recomendations> recs = user.getRecomendationsList();
        List<Products> prods = new ArrayList<>();

        for (Recomendations rec : recs)
        {
            System.out.println("Adding product...");
            prods.add(rec.getProducts());
        }

        if (prods.isEmpty())
        {
            System.out.println("No products found...");
            r.setState(NO_RESULTS_ERROR);
            r.setData("No se encontraron productos");
        } else
        {
            System.out.println("There you go...");
            r.setState(STATE_OK);
            r.setData(mapper.writeValueAsString(prods));
        }
        return r;
    }

    private Result setProductVisibility(String data, String username, boolean show)
            throws SQLException, JsonParseException, JsonMappingException, IOException, NamingException
    {
        Result r = new Result();
        Map<String, Object> map = mapper.readValue(data, Map.class);
        System.out.println("Fetching product...");
        Products p = productsFacade.find((int) map.get("idproducts"));
        if (p != null)
        {
            System.out.println("Product found...");
            p.setShowing(show ? 1 : 0);
            productsFacade.edit(p);
            r.setState(STATE_OK);
            r.setData("Producto " + (show ? "visible..." : "oculto..."));
        } else
        {
            System.out.println("Product not found :(");
            r.setState(NO_RESULTS_ERROR);
            r.setData("Producto inexistente");
        }

        return r;
    }

    private Result updateProduct(String data, String username)
            throws SQLException, JsonParseException, JsonMappingException, IOException
    {
        Result r = new Result();
        Map<String, Object> map = mapper.readValue(data, Map.class);
        System.out.println("fetching product...");
        Products p = productsFacade.find((int) map.get("idproducts"));
        if (p != null)
        {
            String description = (String) map.get("description");
            String name = (String) map.get("name");
            String price = (String) map.get("price");
            int showing = (int) map.get("showing");
            int store = (int) map.get("storesIdstores");
            p.setDescription(description != null ? description : p.getDescription());
            p.setName(name != null ? name : p.getName());
            p.setPrice(price != null ? price : p.getPrice());
            p.setShowing(showing);
            Stores s = storesFacade.find(store);
            p.setStoresIdstores(s != null ? s : p.getStoresIdstores());
            productsFacade.edit(p);
            r.setState(STATE_OK);
            r.setData("Producto actualizado");
        } else
        {
            System.out.println("Product not found :(");
            r.setState(NO_RESULTS_ERROR);
            r.setData("Producto inexistente");
        }
        return r;
    }

    private Result addPurchase(String data, String username) throws IOException
    {
        Result r = new Result();
        Map<String, Object> map = mapper.readValue(data, Map.class);
        int idproducts = (int) map.get("product");
        System.out.println("Fetching user...");
        Users user = usersFacade.getUserByEmail(username);
        System.out.println("Fetching product...");
        Products product = productsFacade.find(idproducts);
        if (product != null)
        {
            boolean found = false;
            System.out.println("Searching product in liked products");
            List<Tastes> tastes = user.getTastesList();
            for (Tastes taste : tastes)
            {
                if (taste.getLiked() && taste.getProducts().getIdproducts() == idproducts)
                {
                    System.out.println("Product found, updating likes");
                    tastesFacade.deleteTaste(idproducts, user.getIdusers());
                    found = true;
                    break;
                }
            }
            if (!found)
            {
                System.out.println("Product not found on liked, searching on recommendations");
                List<Recomendations> recs = user.getRecomendationsList();
                for (Recomendations rec : recs)
                {
                    if (rec.getProducts().getIdproducts() == idproducts)
                    {
                        System.out.println("Product found, updating recomendations");
                        rec.setShowed(true);
                        recomendationsFacade.deleteRecomendation(idproducts, user.getIdusers());
                        found = true;
                        break;
                    }
                }
            }
            System.out.println("Adding purchase...");
            Purchases purchase = new Purchases(user.getIdusers(), idproducts);
            purchase.setPurchaseDate(new Date(System.currentTimeMillis()));
            purchasesFacade.create(purchase);
            r.setState(STATE_OK);
            r.setData("Compra registrada");
        } else
        {
            System.out.println("Product does not exists...");
            r.setState(NO_RESULTS_ERROR);
            r.setData("Producto no existente");
        }
        return r;
    }

    private Result showPurchases(String data, String username) throws IOException
    {
        Result r = new Result();
        Map<String, Object> map = mapper.readValue(data, Map.class);
        int iduser = -1;
        if (map.containsKey("user"))
        {
            iduser = (int) map.get("user");
        } else
        {
            System.out.println("Fetching user...");
            Users user = usersFacade.getUserByEmail(username);
            iduser = user.getIdusers();
        }
        System.out.println("Fetching user's purchases...");
        List<Purchases> purchases = purchasesFacade.listbyUser(iduser);
        List<Products> products = new ArrayList<>();
        for (Purchases purchase : purchases)
        {
            System.out.println("Adding product...");
            products.add(productsFacade.find(purchase.getPurchasesPK().getProductsIdproducts()));
        }
        r.setState(STATE_OK);
        r.setData(mapper.writeValueAsString(products));
        return r;
    }

    private ProductsFacade lookupProductsFacadeBean()
    {
        try
        {
            Context c = new InitialContext();
            return (ProductsFacade) c.lookup("java:global/wishper_ws-1.0-SNAPSHOT/ProductsFacade!com.mill.session.ProductsFacade");
        } catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private TagsFacade lookupTagsFacadeBean()
    {
        try
        {
            Context c = new InitialContext();
            return (TagsFacade) c.lookup("java:global/wishper_ws-1.0-SNAPSHOT/TagsFacade!com.mill.session.TagsFacade");
        } catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ImagesFacade lookupImagesFacadeBean()
    {
        try
        {
            Context c = new InitialContext();
            return (ImagesFacade) c.lookup("java:global/wishper_ws-1.0-SNAPSHOT/ImagesFacade!com.mill.session.ImagesFacade");
        } catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private UsersFacade lookupUsersFacadeBean()
    {
        try
        {
            Context c = new InitialContext();
            return (UsersFacade) c.lookup("java:global/wishper_ws-1.0-SNAPSHOT/UsersFacade!com.mill.session.UsersFacade");
        } catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private TastesFacade lookupTastesFacadeBean()
    {
        try
        {
            Context c = new InitialContext();
            return (TastesFacade) c.lookup("java:global/wishper_ws-1.0-SNAPSHOT/TastesFacade!com.mill.session.TastesFacade");
        } catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private RecomendationsFacade lookupRecomendationsFacadeBean()
    {
        try
        {
            Context c = new InitialContext();
            return (RecomendationsFacade) c.lookup("java:global/wishper_ws-1.0-SNAPSHOT/RecomendationsFacade!com.mill.session.RecomendationsFacade");
        } catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private StoresFacade lookupStoresFacadeBean()
    {
        try
        {
            Context c = new InitialContext();
            return (StoresFacade) c.lookup("java:global/wishper_ws-1.0-SNAPSHOT/StoresFacade!com.mill.session.StoresFacade");
        } catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private PurchasesFacade lookupPurchasesFacadeBean()
    {
        try
        {
            Context c = new InitialContext();
            return (PurchasesFacade) c.lookup("java:global/wishper_ws-1.0-SNAPSHOT/PurchasesFacade!com.mill.session.PurchasesFacade");
        } catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
