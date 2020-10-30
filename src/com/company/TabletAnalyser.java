package com.company;

import java.util.LinkedList;
import java.util.List;

public class TabletAnalyser {

    private static final String SAMSUNG = "Samsung";
    private static final String ASUS = "Asus";

    public static TabletInfo pickBest(List<TabletInfo> tabletInfos, float money) {
        int maxMemoryForPrice = Integer.MIN_VALUE;
        for (TabletInfo i : tabletInfos) {
            if (i.getMemorySize() > maxMemoryForPrice && i.getPrice() <= money) {
                maxMemoryForPrice = i.getMemorySize();
            }
        }
        List<TabletInfo> tabletsWithMaxMemoryForPrice = new LinkedList<>();
        for (TabletInfo i : tabletInfos) {
            if (i.getMemorySize() == maxMemoryForPrice) {
                tabletsWithMaxMemoryForPrice.add(i);
            }
        }
        switch (tabletsWithMaxMemoryForPrice.size()) {
            case 1:
                return tabletsWithMaxMemoryForPrice.get(0);
            case 0:
                return null;
        }

        int maxRating = Integer.MIN_VALUE;
        for (TabletInfo i : tabletsWithMaxMemoryForPrice) {
            if (i.getRating() > maxRating) {
                maxRating = i.getRating();
            }
        }
        List<TabletInfo> tabletsWithMaxMemoryForPriceAndRating = new LinkedList<>();
        for (TabletInfo i : tabletsWithMaxMemoryForPrice) {
            if (i.getRating() == maxRating) {
                tabletsWithMaxMemoryForPriceAndRating.add(i);
            }
        }

        if (tabletsWithMaxMemoryForPriceAndRating.size() == 1) {
            return tabletsWithMaxMemoryForPriceAndRating.get(0);
        }

        for (TabletInfo i : tabletsWithMaxMemoryForPriceAndRating) {
            if (i.getName().contains(SAMSUNG) || i.getName().contains(ASUS)) {
                return i;
            }
        }
        return tabletsWithMaxMemoryForPriceAndRating.get(0);
    }
}
