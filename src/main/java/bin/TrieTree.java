package bin;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;

public class TrieTree {
    String str ="";
    ArrayList<String> result = new ArrayList<String>();
    public TrieNode root;
    TrieTree() // 初始化字典树
    {
        root = new TrieNode();
    }


    public void insert(String str) // 在字典树中插入一个单词
    {
        if (str == null || str.length() == 0)
        {
            return;
        }
        TrieNode node = root;
        char[] letters = str.toCharArray();//将目标单词转换为字符数组
        for (int i = 0, len = str.length(); i < len; i++)
        {
            int pos = letters[i] - 'A';
            if (node.son[pos] == null)  //如果当前节点的儿子节点中没有该字符，则构建一个TrieNode并复值该字符
            {
                node.son[pos] = new TrieNode();
                node.son[pos].val = letters[i];
            }
            else   //如果已经存在，则将由根至该儿子节点组成的字符串模式出现的次数+1
            {
                node.son[pos].num++;
            }
            node = node.son[pos];
        }
        node.isEnd = true;
    }

    public void preTraverse(TrieNode node,String prefix)
    {
        for (TrieNode child:node.son) {
            if (child!=null){
                str+=""+child.val;
                if (child.isEnd){
                    result.add(prefix+str);
                }
                preTraverse(child,prefix);
            }

        }

    }


    public ArrayList<String> hasPrefix(String prefix)
    {

        if (prefix == null || prefix.length() == 0)
        {
            return null;
        }
        TrieNode node = root;
        char[] letters = prefix.toCharArray();
        for (int i = 0, len = prefix.length(); i < len; i++)
        {
            int pos = letters[i] - 'A';
            if (node.son[pos] == null)
            {
                return null;
            }
            else
            {
                node = node.son[pos];
            }
        }
        if (node.son!=null)
        {
            preTraverse(node,prefix);
            return result;
        }

        else
            return null;

       // preTraverse(node);

    }


    public static void main(String[] args) {
        TrieTree tree = new TrieTree();
//        tree.insert("zhang");
//        tree.insert("zhangs");
//        tree.insert("zhangsh");
        tree.insert("zhangshi");
        tree.insert("zhangshiy");
        tree.insert("zhangshiyu");
        tree.insert("zhangshia");

        System.out.println(tree.hasPrefix("zhangshi").toString());
        String s = JSON.toJSONString(tree,true);
        //System.out.println(s);
        //tree.preTraverse(tree.root);

    }
}
