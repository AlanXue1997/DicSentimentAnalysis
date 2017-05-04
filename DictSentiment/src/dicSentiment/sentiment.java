package dicSentiment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class sentiment {
	Set<String> posWordSet,negWordSet,negVodSet,vod1Set,vod2Set,vod3Set,vod4Set,vod5Set,vod6Set;
	StringBuffer sensTxt;
	float tolSens = 0;//句子情感值
	float docSens = 0;//文档情感值
	
	
	public void readDoc(String path,String senPath) throws IOException{
		File doc = new File(path);
		String sigleDoc = null;
		String[] stringList;
		dic();
		StringBuffer readParse, ParseSen;

		BufferedReader docBf = new BufferedReader(new InputStreamReader(new FileInputStream(doc), "utf8"));
		
	
		while ((sigleDoc = docBf.readLine())!= null) {
			readParse = new StringBuffer();
			ParseSen = new StringBuffer();
		    readParse.append("<");
		    ParseSen.append("<");
		    
			stringList = sigleDoc.split(" ");
			for (int i = 0; i < stringList.length; i++) {
				
				if(posWordSet.contains(stringList[i])||negWordSet.contains(stringList[i])||negVodSet.contains(stringList[i])
						||vod1Set.contains(stringList[i])||vod2Set.contains(stringList[i])||vod3Set.contains(stringList[i])
						||vod4Set.contains(stringList[i])||vod5Set.contains(stringList[i])||vod6Set.contains(stringList[i])) {
					
					if (readParse.lastIndexOf(">")>readParse.lastIndexOf("<")) {
						readParse.append("<");
					}
					if (ParseSen.lastIndexOf(">")>ParseSen.lastIndexOf("<")) {
						ParseSen.append("<");
					}
					
					if (negVodSet.contains(stringList[i])){ 
						//System.out.print("NA");
						readParse.append("NA"); ParseSen.append("-0.8,"); }	
					if (vod1Set.contains(stringList[i])) {	
						//System.out.print("DA");
						readParse.append("DA"); ParseSen.append("0.9,"); }
					if (vod2Set.contains(stringList[i])) {  
						//System.out.print("DA");
						readParse.append("DA"); ParseSen.append("0.9,");	}	
					if (vod3Set.contains(stringList[i])) {	
						//System.out.print("DA");
						readParse.append("DA"); ParseSen.append("0.7,");}
					if (vod4Set.contains(stringList[i])) {	
						//System.out.print("DA");
						readParse.append("DA"); ParseSen.append("0.5,");}
					if (vod5Set.contains(stringList[i])) {	
						//System.out.print("DA");
						readParse.append("DA"); ParseSen.append("0.3,");}
					if (vod6Set.contains(stringList[i])) {	
						//System.out.print("DA");
						readParse.append("DA"); ParseSen.append("-0.5,");}
					if (posWordSet.contains(stringList[i])) { 
						//System.out.print("PW");
						readParse.append("PW>"); ParseSen.append("0.8>");
						}
					if (negWordSet.contains(stringList[i])) {
						//System.out.print("PW");
						readParse.append("PW>"); ParseSen.append("-0.8>");
						}
		
				}
			}

			computeSen(readParse.toString(),ParseSen.toString(),senPath);
			
		}

		docBf.close();
		
	}
	
	//读取关键词文件
	public void dic() throws IOException{
		
		BufferedReader posWord = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("Dic/POS/0.8.txt"),"gb2312"));
		BufferedReader negWord = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("Dic/NEG/-0.8.txt"),"gb2312"));
		BufferedReader negVod = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("Dic/NA/-0.8.txt"),"gb2312"));
		BufferedReader vod1 = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("Dic/DA/10.9.txt"),"gb2312"));
		BufferedReader vod2 = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("Dic/DA/20.9.txt"),"gb2312"));
		BufferedReader vod3 = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("Dic/DA/0.7.txt"),"gb2312"));
		BufferedReader vod4 = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("Dic/DA/0.5.txt"),"gb2312"));
		BufferedReader vod5 = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("Dic/DA/0.3.txt"),"gb2312"));	
		BufferedReader vod6 = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("Dic/DA/-0.5.txt"),"gb2312"));

		
		

		posWordSet = new HashSet<String>();
		negWordSet = new HashSet<String>();
		negVodSet = new HashSet<String>();
		vod1Set = new HashSet<String>();
		vod2Set = new HashSet<String>();
		vod3Set = new HashSet<String>();
		vod4Set = new HashSet<String>();
		vod5Set = new HashSet<String>();
		vod6Set = new HashSet<String>();
		
		
		
		String Sb = null;
		while((Sb = posWord.readLine()) != null){
			
			posWordSet.add(Sb);
		}
		while((Sb = negWord.readLine()) != null){
			negWordSet.add(Sb);
		}
		while((Sb = negVod.readLine()) != null){
			negVodSet.add(Sb);
		}
		while((Sb = vod1.readLine()) != null){
			vod1Set.add(Sb);
		}
		while((Sb = vod2.readLine()) != null){
			vod2Set.add(Sb);
		}
		while((Sb = vod3.readLine()) != null){
			vod3Set.add(Sb);
		}
		while((Sb = vod4.readLine()) != null){
			vod4Set.add(Sb);
		}
		while((Sb = vod5.readLine()) != null){
			vod5Set.add(Sb);
		}
		while((Sb = vod6.readLine()) != null){
			vod6Set.add(Sb);
		}
		posWord.close();negWord.close();negVod.close();vod1.close();
		vod2.close();vod3.close();vod4.close();vod5.close();vod6.close();
	}
	
	
	public  void saveSens(String path,String ci) {
		File file = new File(path);
		try {
			if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter  fwriter = new FileWriter(path,true);
		fwriter.write(ci);
		fwriter.flush();
		fwriter.close();
		} catch (Exception e) {
			System.out.print("保存文件出错");
			e.printStackTrace();
		}
		
	}
	
	
	public void  accuracy(String pre,String result) throws Exception {
		File preFile = new File(pre);
		File resultFile = new File(result);
		if (!preFile.exists()||!resultFile.exists()) {
			throw new IllegalArgumentException("没有文件"); 
		}
		BufferedReader preBf = new BufferedReader(new InputStreamReader(new FileInputStream(preFile), "gb2312"));
		BufferedReader resultBf = new BufferedReader(new InputStreamReader(new FileInputStream(resultFile), "gb2312"));
		
		double a = 0;
		double b = 0;
		String presb = null;
		String resultsb = null;
		while ((presb = preBf.readLine())!=null && (resultsb = resultBf.readLine())!=null) {
			if(presb.equals(resultsb)) {
				a = a+1;
				b = b+1;

			}else {
				b = b+1;
			}
		}
		preBf.close();resultBf.close();
		
		System.out.println("准确率为： "+a/b);
	
	}
	
	//计算文件每行句子的情感值
	public double computeSen(String doc,String value,String senPath){
		String regx1 = "<(.+?)>";
		Pattern p = Pattern.compile(regx1);
        Matcher m1 = p.matcher(doc);
        Matcher m2 = p.matcher(value);
        
        ArrayList<String> doclist   = new ArrayList<>();
        ArrayList<String> valuelist = new ArrayList<>();
        while (m2.find()) {
        	valuelist.add(m2.group(1));
			
		}
		while (m1.find()) {
			doclist.add(m1.group(1));
		}
		
		Iterator<String> dociter = doclist.iterator();
		Iterator<String> valueiter = valuelist.iterator();
		while(dociter.hasNext()){
			String juzi = null;
			juzi = dociter.next();
//			System.out.print(juzi = dociter.next());
			String[] cpvalue =  valueiter.next().split(",");
//			for (int i = 0; i < cpvalue.length; i++) {
//				System.out.print(" "+cpvalue[i]+" ");
//			}
			
			
			if (juzi.equals("PW")) { tolSens = Float.parseFloat(cpvalue[0]); }
			if (juzi.equals("NAPW")) {
				tolSens = Float.parseFloat(cpvalue[0])*Float.parseFloat(cpvalue[1]);
			}
			if (juzi.equals("NANAPW")) {
				tolSens = Float.parseFloat(cpvalue[2]);
			}
			if (juzi.equals("DAPW")) {
				if (Float.parseFloat(cpvalue[1])>0) {
					tolSens = Float.parseFloat(cpvalue[1])+(1-Float.parseFloat(cpvalue[1]))*Float.parseFloat(cpvalue[0]);
				}else {
					tolSens = Float.parseFloat(cpvalue[1])+(-1-Float.parseFloat(cpvalue[1]))*Float.parseFloat(cpvalue[0]);
				}
			}
			if (juzi.equals("DADAPW")) {
				
					tolSens = Float.parseFloat(cpvalue[2])+
							(1-Float.parseFloat(cpvalue[2]))*Float.parseFloat(cpvalue[0])+
							(1-Float.parseFloat(cpvalue[2])-(1-Float.parseFloat(cpvalue[2]))*Float.parseFloat(cpvalue[0]))*Float.parseFloat(cpvalue[1]);
			}
			if (juzi.equals("NADAPW")) {
				tolSens = Float.parseFloat(cpvalue[2])+(1-Float.parseFloat(cpvalue[0]))*(Float.parseFloat(cpvalue[1])-2);
			}
			
			if (juzi.equals("DANAPW")) {
				tolSens = Float.parseFloat(cpvalue[2])*Float.parseFloat(cpvalue[1])+
						(-1-Float.parseFloat(cpvalue[2]))*Float.parseFloat(cpvalue[1])*Float.parseFloat(cpvalue[1]);
			}
			
			docSens = tolSens+docSens;
		}
		
		
		sensTxt = new StringBuffer();
		if (docSens>0) {
			sensTxt.append("pos"+"\r\n");
			
		}else if (docSens<0) {
			sensTxt.append("neg"+"\r\n");
		}else{
			sensTxt.append("neutral"+"\r\n");
		}
		saveSens(senPath,sensTxt.toString());
		
		tolSens = 0;
		docSens = 0;
		return tolSens;	
	}	
}
