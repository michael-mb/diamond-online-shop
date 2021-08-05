package com.shop.diamond.general;

import com.shop.diamond.item.entities.Brand;
import com.shop.diamond.item.entities.Item;
import com.shop.diamond.item.services.ItemService;
import com.shop.diamond.user.entities.TestUser;
import com.shop.diamond.user.entities.User;
import com.shop.diamond.user.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Initializer {
    private static final Logger LOG = LoggerFactory.getLogger(Initializer.class);

    public ItemService itemService;
    public UserService userService;

    @Autowired
    public Initializer(ItemService itemService, UserService userService) {
        this.itemService = itemService;
        this.userService = userService;
        setupDemoItems();
        setupDemoUsers();
    }

    public void setupDemoItems(){
        LOG.info("Setup demo Items");
        itemService.save(new Item(20L, "Easy Polo Black Edition", "images/shop/product12.jpg",
                "HOSE AUS REINER SEIDE MIT HOHEM BUND. ABGESETZTER STOFF IM GLEICHEN FARBTON AN DER VORDERSEITE. WEITES BEIN. MIT PATTE VERDECKTE KNOPFLEISTE VORNE.",
                20L, false , Brand.Zara));

        itemService.save(new Item(40L, "Easy Polo Black Edition", "images/shop/product8.jpg",
                "HOSE AUS REINER SEIDE MIT HOHEM BUND. ABGESETZTER STOFF IM GLEICHEN FARBTON AN DER VORDERSEITE. WEITES BEIN. MIT PATTE VERDECKTE KNOPFLEISTE VORNE.",
                20L, true , Brand.Louis_Vuitton));

        itemService.save(new Item(15L, "Easy Polo Black Edition", "images/shop/product9.jpg",
                "Shalom.",
                20L, false , Brand.Tommy));
        itemService.save(new Item(225L, "Easy Polo Black Edition", "images/shop/product11.jpg",
                "HOSE AUS REINER SEIDE MIT HOHEM BUND. ABGESETZTER STOFF IM GLEICHEN FARBTON AN DER VORDERSEITE. WEITES BEIN. MIT PATTE VERDECKTE KNOPFLEISTE VORNE.",
                20L, true , Brand.Louis_Vuitton));

        itemService.save(new Item(119L, "Easy Polo Black Edition", "images/shop/product11.jpg",
                "HOSE AUS REINER SEIDE MIT HOHEM BUND. ABGESETZTER STOFF IM GLEICHEN FARBTON AN DER VORDERSEITE. WEITES BEIN. MIT PATTE VERDECKTE KNOPFLEISTE VORNE.",
                20L, true , Brand.Channel));
        itemService.save(new Item(315L, "Easy Polo Black Edition", "images/shop/product10.jpg",
                "HOSE AUS REINER SEIDE MIT HOHEM BUND. ABGESETZTER STOFF IM GLEICHEN FARBTON AN DER VORDERSEITE. WEITES BEIN. MIT PATTE VERDECKTE KNOPFLEISTE VORNE.",
                20L, false , Brand.Channel));
        itemService.save(new Item(218L, "Easy Polo Black Edition", "images/shop/product9.jpg",
                "HOSE AUS REINER SEIDE MIT HOHEM BUND. ABGESETZTER STOFF IM GLEICHEN FARBTON AN DER VORDERSEITE. WEITES BEIN. MIT PATTE VERDECKTE KNOPFLEISTE VORNE.",
                20L, false , Brand.Zara));
        itemService.save(new Item(189L, "Easy Polo Black Edition", "images/shop/product9.jpg",
                "HOSE AUS REINER SEIDE MIT HOHEM BUND. ABGESETZTER STOFF IM GLEICHEN FARBTON AN DER VORDERSEITE. WEITES BEIN. MIT PATTE VERDECKTE KNOPFLEISTE VORNE.",
                20L, true , Brand.Tommy));
        itemService.save(new Item(25L, "Easy Polo Black Edition", "images/shop/product10.jpg",
                "HOSE AUS REINER SEIDE MIT HOHEM BUND. ABGESETZTER STOFF IM GLEICHEN FARBTON AN DER VORDERSEITE. WEITES BEIN. MIT PATTE VERDECKTE KNOPFLEISTE VORNE.",
                20L, false , Brand.Zara));

        itemService.save(new Item(499L, "Easy Polo Black Edition", "images/shop/product9.jpg",
                "HOSE AUS REINER SEIDE MIT HOHEM BUND. ABGESETZTER STOFF IM GLEICHEN FARBTON AN DER VORDERSEITE. WEITES BEIN. MIT PATTE VERDECKTE KNOPFLEISTE VORNE.",
                20L, true , Brand.Tommy));
        itemService.save(new Item(502L, "Easy Polo Black Edition", "images/shop/product9.jpg",
                "HOSE AUS REINER SEIDE MIT HOHEM BUND. ABGESETZTER STOFF IM GLEICHEN FARBTON AN DER VORDERSEITE. WEITES BEIN. MIT PATTE VERDECKTE KNOPFLEISTE VORNE.",
                20L, false , Brand.Zara));
        itemService.save(new Item(440L, "Easy Polo Black Edition", "images/shop/product9.jpg",
                "HOSE AUS REINER SEIDE MIT HOHEM BUND. ABGESETZTER STOFF IM GLEICHEN FARBTON AN DER VORDERSEITE. WEITES BEIN. MIT PATTE VERDECKTE KNOPFLEISTE VORNE.",
                20L, true , Brand.Tommy));

    }

    public void setupDemoUsers(){

        LOG.info("Creating default Users.");

        User user;
        for (TestUser testUser : TestUser.values()) {
            user = new User(testUser.firstName , testUser.lastName , testUser.email ,
                    testUser.password , testUser.grantedAuthorities);

            userService.generateAndSaveNewValidationTokenForUser(user);
            userService.rehashPassword(testUser.password , user);
        }
    }
}

