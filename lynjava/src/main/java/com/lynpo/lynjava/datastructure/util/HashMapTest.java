package com.lynpo.lynjava.datastructure.util;

/**
 * Create by fujw on 2018/10/15.
 * *
 * HashMapTest
 */
public class HashMapTest {

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < (1 << 18) - 1; i++) {
//            if (i == 98 || i == 99) {
//                System.out.println("table to node");
//            }
            map.put("s-" + i, "s of i-" + i);
        }
//        map.put("s-7", "s of i-7");
//        map.put("s-8", "s of i-8");
//        map.put("s-9", "s of i-9");
//        map.put("s-63", "s of i-63");
//        map.put("s-64", "s of i-64");
//        map.put("s-65", "s of i-65");

//        for (String key : map.keySet()) {
//            System.out.println("value in map of key[" + key + "] is:" + map.get(key));
//        }
//                System.out.println(1<<30);

        int tree_node_count = 0;
        int non_tree_node_count = 0;
        int node_count = 0;
        int keySet_foreach_count = 0;
        int node_found_in_keySet_count = 0;
        int node_null_count = 0;
        for (HashMap.Node<String, String> node : ((HashMap<String, String>) map).table) {
            if (node != null) {
                if (node instanceof HashMap.TreeNode) {
                    tree_node_count++;
                    System.out.println("node of hash[" + node.hash + " instanceof HashMap.TreeNode");
                } else {
                    non_tree_node_count++;
                }

                node_count++;
                HashMap.Node<String, String> p = node.next;
//                boolean parent = true;
//                while (p != null) {
//                    if (parent) {
//                        int n_hash = node.hash;
//                        System.out.println("n_hash of " + node.key + " in binary:" + n_hash);
//                        System.out.println("n_hash of " + node.key + " in binary:" + Integer.toBinaryString(n_hash));
//                    }
//                    parent = false;
//
//                    int p_hash = p.hash;
//                    System.out.println("p_hash of " + p.key + " in binary:" + p_hash);
//                    System.out.println("p_hash of " + p.key + " in binary:" + Integer.toBinaryString(p_hash));
//
//                    p = p.next;
//                }

//                    System.out.println("map's table-node of hash[" + node.hash + "] is: node-key:" + node.key + ", node-value:" + node.value);
            } else {
                node_null_count++;
            }
        }

//        for (String key : map.keySet()) {
//            String key_not_in_table = "";
//            keySet_foreach_count++;
//            for (HashMap.Node<String, String> node : ((HashMap<String, String>) map).table) {
//                if (node != null) {
//                    if (key.equals(node.key)) {
//                        key_not_in_table = "";
//                        node_found_in_keySet_count++;
//                        break;
//                    }
//                    key_not_in_table = key;
//                }
//            }
//
//            if (key_not_in_table.length() > 0) {
//                System.out.println("key_not_in_table:" + key_not_in_table);
//            }
//        }

        System.out.println("map table length:" + ((HashMap<String, String>) map).table.length); // power of two
        System.out.println("map size:" + map.size());
        System.out.println("map entrySet size:" + map.entrySet().size());
        System.out.println("map keySet size:" + map.keySet().size());

        System.out.println("tree_node_count:" + tree_node_count);
//        System.out.println("keySet_foreach_count:" + keySet_foreach_count);
//        System.out.println("node_found_in_keySet_count:" + node_found_in_keySet_count);
        System.out.println("non_tree_node_count:" + non_tree_node_count);
//        System.out.println("node_count:" + node_count);
//        System.out.println("node_null_count:" + node_null_count);
    }
}
