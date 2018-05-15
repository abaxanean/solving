/*
 * Copyright 2017 Mobile Iron, Inc.
 * All rights reserved.
 */

package com.bax.poi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

//import org.apache.poi.poifs.filesystem.DirectoryEntry;
//import org.apache.poi.poifs.filesystem.DocumentEntry;
//import org.apache.poi.poifs.filesystem.Entry;
//import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;

@EnableAutoConfiguration
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
//        NPOIFSFileSystem fileSystem = new NPOIFSFileSystem(Files.newInputStream(Paths.get("/Users/abaxanean/Downloads/MobileIron_Bridge.msi")));
//        NPOIFSFileSystem fileSystem = new NPOIFSFileSystem(new File("/Users/abaxanean/Downloads/MobileIron_Bridge.msi"));
//        NPropertyTable table = fileSystem.getPropertyTable();
//        System.out.println(table);
//        fileSystem.getPropertyTable();
//        read(fileSystem.getRoot(), "");
    }

//    public static void read(DirectoryEntry dir, String tab) {
//        for (Entry entry : dir) {
////            System.out.println("found entry: " + entry.getName());
//            if (entry instanceof DirectoryEntry) {
//                System.out.println(entry.getName());
//                read((DirectoryEntry)entry, tab + "  ");
//            } else if (entry instanceof DocumentEntry) {
//                System.out.println(tab + entry.getName());
//            } else {
//                // currently, either an Entry is a DirectoryEntry or a DocumentEntry,
//                // but in the future, there may be other entry subinterfaces. The
//                // internal data structure certainly allows for a lot more entry types.
//            }
//        }
//    }

}
