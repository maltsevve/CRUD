//package com.maltsevve.crud.repository;
//
//import com.maltsevve.crud.model.Post;
//
//class test {
//    public static void main(String[] args) {
//        JavaIOPostRepositoryImpl post = new JavaIOPostRepositoryImpl();
//        JavaIOLabelRepositoryImpl lb = new JavaIOLabelRepositoryImpl();
//
//        // не записывает второй объект, так как некорректно работает save
//        // в следствии неработающего readToList
//        post.save(new Post("hhhhh", lb.getAll()));
//        post.save(new Post("fffff", lb.getAll()));
//    }
//}
