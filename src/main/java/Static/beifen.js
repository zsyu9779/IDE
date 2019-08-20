//字典树
var triNode = function(key){
    this.key = key;
    this.son = [];
    this.isWord = false;//用于单词标记
}
var tree = function(){
    this.root = new triNode(null);
}
tree.prototype={
    insertData:function(stringData){
        //用于外部调用插入，目的是从根节点开始插入
        this.insert(stringData,this.root)
    },
    insert:function(stringData,node){
        //用于内部自身递归调用，层层判断是否存在或是否要插入
        if(stringData==''){
            //字符串为空，直接返回结束
            return;
        }
        //获取子节点
        var son = this.getSon(node);
        var haveData = null;
        //声明一个变量用来存储字符串第一个字符和子节点相同的节点，方便后续节点递归遍历
        for(var i in son){
            if(son[i].key==stringData[0]){
                haveData = son[i]
            }
        }
        //document.write(haveData)
        if(haveData){
            if(stringData.length==1){
                haveData.isWord = true;
                //document.write("666")
            }
            //havaData存在说明在子节点找到了，然后进行深入节点查找
            this.insert(stringData.substring(1),haveData)
        }else{
            if(son.length==0){
                //如果子节点为空，则直接插入
                var node = new triNode(stringData[0]);
                son.push(node);
                if(stringData.length==1){
                    node.isWord = true;
                }
                //插入完毕后将后续字符串继续插入
                this.insert(stringData.substring(1),node);
            }else{
                var node = new triNode(stringData[0]);
                //将子节点的key进行排序插入，方便后续进行二分法查找，加快查找效率
                var vlPosition = 0;
                for(var j in son){
                    if(son[j].key<stringData[0]){
                        vlPosition++;
                    }
                }
                if(stringData.length==1){
                    node.isWord = true;
                }
                //子节点插入
                son.splice(vlPosition,0,node);
                //插入完毕后将后续字符串继续插入
                this.insert(stringData.substring(1),node);
            }
        }
    },
    justContentData:function(stringData){
        if(stringData==''){
            return 0
        }else{
            return this.justContent(stringData,this.root);
        }
    },
    //验证字典树里是否包含指定字符串,包含返回1
    // justContent:function(stringData,node){
    //     if(stringData==''){
    //         //字符串为空，直接返回结束
    //         return 1;
    //     }
    //     var son = this.getSon(node);
    //     var havaData = null;
    //     for(var i in son){
    //         if(son[i].key==stringData[0]){
    //             havaData = son[i];
    //         }
    //     }
    //     if(havaData){
    //         return this.justContent(stringData.substring(1),havaData)
    //     }else{
    //         return 0
    //     }
    // },
    countBeforeData:function(stringData){
        if(stringData==''){
            return 0;
        }
        var node = this.searchBeforeNode(stringData,this.root);
        if(!node){
            return 0;
        }
        document.write(this.countBefore(node,0));
        return this.countBefore(node,0);
    },
    searchBeforeNode:function(stringData,node){
        if(stringData==''){
            //字符串为空，直接返回结束
            return node;
        }

        var son = this.getSon(node);
        var havaData = null;

        for(var i in son){
            if(son[i].key==stringData[0]){
                havaData = son[i];
            }
        }
        if(havaData){
            return this.searchBeforeNode(stringData.substring(1),havaData)
        }else{
            return null
        }
    },

    aaa:function(stringData,node){
        if(stringData==''){
            //字符串为空，直接返回结束
            return node;
        }
        var result = "";
        var son = this.getSon(node);
        var haveData = null;
        var key= null;
        var isword = null;
        var relist = new Array();
        for (var i in son){
            if(son[i].key==stringData[0]){
                haveData = son[i];
                key=son[i].key;
                isword = son[i].isWord;

            }
        }
        if(haveData){
            result+=key
            if (isword) {
                relist.push(result);
            }
            this.aaa(stringData.substring(1),haveData);
            return relist;
        }else {
            return null;
        }
    },
    countBefore:function(node,num){
        if(node.isWord){
            num++;
        }
        var son  = this.getSon(node);
        var havaData = null;
        for(var i in son){
            num=this.countBefore(son[i],num);
        }
        return num;
    },

    getSon:function(node){
        //获取子节点
        return node.son;
    }
}
var msd = new tree()
//插入数据
msd.insertData("hello");
msd.insertData("helo");
msd.insertData("healo");
msd.insertData("haslo");
document.write(msd.aaa("he",msd.root))
//var  s = JSON.toJSONString(msd,true)
//document.write(s)
//前缀数量
//document.write(msd.justContent("he",msd.root))
//msd.justContent("he",msd.root)
//msd.countBeforeData("h");