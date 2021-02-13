# K-dTree

[![](https://jitpack.io/v/MarcoCiaramella/K-dTree.svg)](https://jitpack.io/#MarcoCiaramella/K-dTree)

An implementation of k-d tree as an Android library.

## How to import in your Android project
Add JitPack in your root build.gradle at the end of repositories:

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
}
```

Add the dependency

```
dependencies {
	        implementation 'com.github.MarcoCiaramella:K-dTree:1.0.1'
}
```

## How to use

```java
KdTree kdTree = new KdTree(new Node(0,0));
kdTree.insert(new Node(1,2));
kdTree.insert(new Node(-3,1));
kdTree.insert(new Node(2,-2));
kdTree.insert(new Node(4,9));
kdTree.insert(new Node(-5,12));
Node nearest = kdTree.searchNearest(new Node(-4,1));
```
