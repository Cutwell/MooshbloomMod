package com.mooshbloommod.mobs.config;

import java.util.List;

/**
 * This holds the baked (runtime) values for our config.
 * These values should never be from changed outside this package.
 * This can be split into multiple classes (Server, Client, Player, Common)
 * but has been kept in one class for simplicity
 *
 * @author Cadiboo
 */
public final class MooshbloomModConfig {

    //Common


    public static List<String> moobloomSpawnBiomes;
    public static int moobloomWeight;
    public static int moobloomGroupMin;
    public static int moobloomGroupMax;


    // Client

    public static boolean showDescription;

    // Server


}