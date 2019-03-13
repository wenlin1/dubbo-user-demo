package wl.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 敏感词检验工具类
 * @author Wl
 * @date 2019/3/8
 */
public class SensitiveWordUtil {
    //集合参数
    private static  Map sensitiveWordMap;
    // 最小匹配规则
    public static int minMatchTYpe = 1;
    // 最大匹配规则
    public static int maxMatchType = 2;
    //正则变大匹配
    public static String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#� \uE009\uF8F5￥%……& amp;*（）——+|{}【】‘；：”“’。，、？]";
    public static  Pattern p = Pattern.compile(regEx);


    /**
     * 给sensitiveWordMap赋值
     * @return
     */
    public static  Map getInstance() {
        if (sensitiveWordMap == null) {
            sensitiveWordMap=initKeyWord();
        }
        return sensitiveWordMap;
    }
    /**
     * 初始化敏感词库
     */
    public  static Map initKeyWord(){
        try {
            //读取敏感词库
            Set<String> keyWordSet = readSensitiveWordFile();
            //将敏感词库加入到HashMap中
            sensitiveWordMap=addSensitiveWordToHashMap(keyWordSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sensitiveWordMap;
    }
    /**
     * @通过敏感词set集合构建敏感词库map
     * @这个set集合可以通过数据库查询敏感词添加到set或者敏感词的文本库
     * 读进来之后，添加到set,看自己实际项目是啥样的
     */
    public static Map<String, String> addSensitiveWordToHashMap(Set<String> keyWordSet) {
        Map<String, String> sensitiveWordMap = new HashMap<String, String>(keyWordSet.size());
        String key = null;
        Map nowMap = null;
        Map<String, String> newWorMap = null;
        Iterator<String> iterator = keyWordSet.iterator();
        while (iterator.hasNext()) {
            // 关键字
            key = iterator.next();
            nowMap = sensitiveWordMap;
            for (int i = 0; i < key.length(); i++) {
                // 转换成char型
                char keyChar = key.charAt(i);
                // 获取
                Object wordMap = nowMap.get(keyChar);
                // 如果存在该key，直接赋值
                if (wordMap != null) {
                    nowMap = (Map) wordMap;
                } else {
                    // 不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWorMap = new HashMap<String, String>();
                    // 不是最后一个
                    newWorMap.put("isEnd", "0");
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }

                if (i == key.length() - 1) {
                    // 最后一个
                    nowMap.put("isEnd", "1");
                }
            }
        }
        return sensitiveWordMap;
    }

    /**
     * 判断文字是否包含敏感字符
     * @param txt
     * @param matchType 1：最小匹配规则，2：最大匹配规则
     * @param sensitiveWordMap
     * @return
     */
    public static boolean isContaintSensitiveWord(String txt, int matchType, Map sensitiveWordMap) {
        boolean flag = false;
        for (int i = 0; i < txt.length(); i++) {
            // 判断是否包含敏感字符
            int matchFlag = CheckSensitiveWord(txt, i, matchType);
            if (matchFlag > 0) {
                // 大于0存在，返回true
                flag = true;
            }
        }
        return flag;
    }


    /**
     * 获取文字中的敏感词
     * @author wl
     * @date 2019年3月28日 下午14:10:52
     * @param txt 文字
     * @param matchType 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
     * @return
     * @version 1.0
     */
    public static Set<String> getSensitiveWord(String txt, int matchType){
        Set<String> sensitiveWordList = new HashSet<String>();
        for(int i = 0 , length = txt.length(); i < length ; i++){
            //判断是否包含敏感字符
            int index = CheckSensitiveWord(txt, i, matchType);
            //存在,加入list中
            if(index > 0){
                Matcher m = p.matcher(txt);
                if(m.find()) {
                    txt = m.replaceAll("").trim();
                    sensitiveWordList.add(txt.substring(i, i + index));
                }else{
                    sensitiveWordList.add(txt.substring(i, i + index));
                }
                //减1的原因，是因为for会自增
                i = i + index - 1;
            }
        }
        return sensitiveWordList;
    }

    /**
     * 替换敏感字字符
     * @author wl
     * @date 2019年3月8日 下午14:12:07
     * @param txt
     * @param matchType
     * @param replaceChar 替换字符，默认*
     * @version 1.0
     */
    public static String replaceSensitiveWord(String txt,int matchType,String replaceChar){
        String resultTxt = txt;
        //获取所有的敏感词
        Set<String> set = getSensitiveWord(txt, matchType);
        Iterator<String> iterator = set.iterator();
        String word = null;
        String replaceString = null;
        while (iterator.hasNext()) {
            word = iterator.next();
            replaceString = getReplaceChars(replaceChar, word.length());
            resultTxt = resultTxt.replaceAll(word, replaceString);
        }

        return resultTxt;
    }

    /**
     * 获取替换字符串
     */
    private static String getReplaceChars(String replaceChar, int length) {
        String resultReplace = replaceChar;
        for (int i = 1; i < length; i++) {
            resultReplace += replaceChar;
        }
        return resultReplace;
    }

    /**
     * 考虑到有些字符串需要校验特殊字符：1.先校验特殊字符是否为敏感字符，2.再检验去除特殊字符情况
     * 推荐使用
     * 校验敏感字
     * @param txt 要检验的字符串
     * @param beginIndex 从第几位开始向后匹配 默认是0
     * @param matchType  最小，做小匹配规则 1,2
     * @return
     */

    public static int  CheckSensitiveWord(String txt,int beginIndex,int matchType){
        //敏感词结束标识位：用于敏感词只有1位的情况
        boolean  flag = false;
        //匹配标识数默认为0
        int matchFlag = 0;
        char word = 0;
        Map nowMap =sensitiveWordMap;
        Map nowsMap=sensitiveWordMap;
        for(int i = beginIndex , length = txt.length(); i < length ; i++){
            word = txt.charAt(i);
            //获取指定key
            nowMap = (Map) nowMap.get(word);
            //存在，则判断是否为最后一个
            if(nowMap != null){
                //找到相应key，匹配标识+1
                matchFlag++;
                //如果为最后一个匹配规则,结束循环，返回匹配标识数
                if("1".equals(nowMap.get("isEnd"))){
                    //结束标志位为true
                    flag = true;
                    //最小规则，直接返回,最大规则还需继续查找
                    if(SensitiveWordUtil.minMatchTYpe == matchType){
                        break;
                    }
                }
            }
            //不存在，直接返回
            else{
                break;
            }
        }
        if(txt!= null && matchFlag!=txt.length()) {
            matchFlag=0;
            Matcher m = p.matcher(txt);
            txt = m.replaceAll("").trim();
            for (int i = beginIndex, length = txt.length(); i < length; i++) {
                word = txt.charAt(i);
                //获取指定key
                nowsMap = (Map) nowsMap.get(word);
                //存在，则判断是否为最后一个
                if (nowsMap != null) {
                    //找到相应key，匹配标识+1
                    matchFlag++;
                    //如果为最后一个匹配规则,结束循环，返回匹配标识数
                    if ("1".equals(nowsMap.get("isEnd"))) {
                        //结束标志位为true
                        flag = true;
                        //最小规则，直接返回,最大规则还需继续查找
                        if (SensitiveWordUtil.minMatchTYpe == matchType) {
                            break;
                        }
                    }
                }
                //不存在，直接返回
                else {
                    break;
                }
            }
        }
        //长度必须大于等于1，为词
        if(matchFlag < 1 || !flag){
            matchFlag = 0;
        }
        return matchFlag;
    }

    static Set<String> readSensitiveWordFile() throws Exception{
        Set<String> set = null;
        File file = new File("D:/word.txt");
        InputStreamReader read = new InputStreamReader(new FileInputStream(file));
        try {
            if(file.isFile() && file.exists()){
                set = new HashSet<String>();
                BufferedReader bufferedReader = new BufferedReader(read);
                String txt = null;
                //读取文件，将文件内容放入到set中
                while((txt = bufferedReader.readLine()) != null){
                    set.add(txt);
                }
            }
            else{
                throw new Exception("敏感词库文件不存在");
            }
        } catch (Exception e) {
            throw e;
        }finally{
            read.close();
        }
        return set;
    }


}
