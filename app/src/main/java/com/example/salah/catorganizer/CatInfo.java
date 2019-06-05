package com.example.salah.catorganizer;

public class CatInfo {

    String name;
    String imageUri;
    boolean loaded;

    CatInfo(String _describe, String _imageUri) {
        name = _describe;
        imageUri = _imageUri;
        loaded = false;
    }
}
