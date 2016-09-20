package WigMath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.broad.igv.bbfile.BBFileReader;
import org.broad.igv.bbfile.BigWigIterator;
import org.broad.igv.bbfile.WigItem;

import GenomeFileReaders.bedFileReader;
import Node.TagNode;

public class WigPuller {
	private static String wig1 = null;
	private static String wig2 = null;
	private static String bed = null;
	public static void main(String[] args) throws IOException {
		for (int i = 0; i < args.length; i++) {

			switch (args[i].charAt((1))) {
			case'a':
				wig1 = args[i+1];
				i++;
				break;
			case'b':
				wig2 = args[i+1];
				i++;
				break;
			case'r':
				bed = args[i+1];
				i++;
				break;
			case'h':
				printUsage();
				System.exit(1);
			}
		}
		
		if (wig1 == null || wig2 == null || bed == null){
			printUsage();
			System.exit(1);
		}
		bedFileReader reader = new bedFileReader(bed);
		ArrayList<TagNode> bedData = reader.getData();
		reader = null;
		BBFileReader wigReader1 = new BBFileReader(wig1);
		BBFileReader wigReader2 = new BBFileReader(wig2);
		ArrayList<double[]> mat = new ArrayList<double[]>();
		for (int i = 0; i < bedData.size();i++){
			String chrom = bedData.get(i).getChrom();
			int start = bedData.get(i).getStart();
			int stop = bedData.get(i).getStop();
			BigWigIterator iter1 = wigReader1.getBigWigIterator(chrom, start, chrom, stop, false);
			HashMap<Integer,double[]> map = new HashMap<Integer,double[]>();
			for (int a = start;a < stop;a++){
				double[] tmp = new double[2];
				map.put(a, tmp);
			}
			while(iter1.hasNext()){
				WigItem item = iter1.next();
				int begin = item.getStartBase();
				int end = item.getEndBase();
				double value = item.getWigValue();
				for (int a = begin;a < end;a++){
					if (a >= start && a < stop){
						double[] tmp = map.get(a);
						tmp[0] = value;
						map.put(a, tmp);
					}
				}
				
			}
			BigWigIterator iter2 = wigReader2.getBigWigIterator(chrom, start, chrom, stop,false);
			while(iter2.hasNext()){
				WigItem item = iter2.next();
				int begin = item.getStartBase();
				int end = item.getEndBase();
				double value = item.getWigValue();
				for (int a = begin;a < end;a++){
					if (a >= start && a < stop){
						double[] tmp = map.get(a);
						tmp[1] = value;
						map.put(a, tmp);
					}
				}
				
			}
			for (int a = start;a < stop;a++){
				System.out.println(map.get(a)[0]+","+map.get(a)[1]);
			}
		
			
		}
		
	}

	public static void printUsage(){
		System.out.println("Usage: java -jar WigPuller.jar <options>");
		System.out.println("Required Parameters:");
		System.out.println("\t-a <WIG/BigWIG> Wig File One");
		System.out.println("\t-b <WIG/BigWIG> Wig File Two");
		System.out.println("\t-r <BED> Regions of interest in BED format");
		System.out.println("\t-h Print this help message and exit");
		System.out.println("****IMPORTANT NOTE: java 7 required!!!****");
	}
}
