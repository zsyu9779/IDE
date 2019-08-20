package bin;

public  class TrieNode // 字典树节点
{
    public int num;// 有多少单词通过这个节点,即由根至该节点组成的字符串模式出现的次数
    public TrieNode[] son;// 所有的儿子节点
    public boolean isEnd;// 是不是最后一个节点
    public char val;// 节点的值

    TrieNode()
    {
        num = 1;
        son = new TrieNode[58];
        //    isEnd = false;

    }

}
