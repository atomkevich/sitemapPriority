package com.epam;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * author alina on 16.6.15.
 */
public class Dictionary {
    private final static List<String> classNameInventoryDictionary =
            Lists.newArrayList(
                    "vehicle_search_wrapper",
                    "rows",
                    "searchresults",
                    "inventory_v2_rows",
                    "vsr",
                    "inventoryList",
                    "inventory-list");

    private final static List<String> menuLinkDictionary =
            Lists.newArrayList(
                    "/new-inventory/index.htm",
                    "/used-inventory/index.htm",
                    "/vehicles/new/continental",
                    "/vehicles/preowned/continental",
                    "/bellevue-new-cars",
                    "/bellevue-used-cars",
                    "/inventory/newsearch/New/",
                    "/inventory/newsearch/Used/",
                    "/search-new-vehicles",
                    "/search-pre-owned-vehicles",
                    "VehicleSearchResults?search=new",
                    "VehicleSearchResults?search=preowned",
                    "/new-inventory/index.htm",
                    "/used-inventory/index.htm",
                    "/new-cars/",
                    "/used-cars/",
                    "/Tulsa/For-Sale/New/",
                    "/Tulsa/For-Sale/Used/",
                    "/Murfreesboro/For-Sale/New/",
                    "/Murfreesboro/For-Sale/Used/",
                    "/Nashville/For-Sale/New/",
                    "/Nashville/For-Sale/Used/",
                    "/Akron-OH/For-Sale/New/",
                    "/Akron-OH/For-Sale/Used/",
                    "VehicleSearchResults?search=new",
                    "VehicleSearchResults?search=preowned",
                    "/new-inventory/index.htm",
                    "/used-inventory/index.htm",
                    "/new-cars-sacramento-ca",
                    "/used-cars-sacramento-ca",
                    "/new-inventory/index.htm",
                    "/used-inventory/index.htm",
                    "/ford.aspx",
                    "/lincoln-inventory.aspx",
                    "http://www.magiccityford.com/used-cars.aspx",
                    "/NewKIACars",
                    "/UsedCars"
            );
    public final static List<String> menuDictionary =
            Lists.newArrayList("All New Inventory",
            "All Used Inventory",
            "New Vehicles",
            "Pre-Owned Vehicles",
            "New Inventory",
            "New Fords",
            "Pre-Owned",
            "New Vehicles",
            "Pre-owned Vehicles",
            "New Vehicles",
            "Used Vehicles",
            "View All Inventory",
            "Used  Inventory",
            "Search New",
            "Search Pre Owned",
            "Pre-Owned",
            "New Cars",
            "Used Cars",
            "View New Ford Inventory",
            "View New Lincoln Inventory",
            "View Used Inventory",
            "Browse New Inventory",
            "Browse Used Inventory");
    public final static List<String> tagDictinary = Lists.newArrayList("navbar", "navigation", "nav", "menu");

    public static final String[] DICTIONARY_NEW = {
            "All New Inventory",
            "New Vehicles",
            "New Inventory",
            "New Fords",
            "Search New",
            "New Cars",
            "View All Inventory",
            "New FIAT Inventory",
            "New FIAT Vehicle Inventory",
    };

    public static final String[] DICTIONARY_USED = {
            "All Used Inventory",
            "Pre-Owned Vehicles",
            "Pre-Owned",
            "Used Vehicles",
            "Used  Inventory",
    };
}
