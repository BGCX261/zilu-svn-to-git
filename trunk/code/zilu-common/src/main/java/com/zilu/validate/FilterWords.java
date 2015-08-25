package com.zilu.validate;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;


public class FilterWords {
	Set<String> filterWords;
	
	protected static final Logger logger = Logger.getLogger(FilterWords.class);
	
	boolean[] firstWords = new boolean[Character.MAX_VALUE];
	
	int[] endWords = new int[Character.MAX_VALUE];
	
	
	public FilterWords(Set<String> filterWords) {
		this.filterWords = filterWords;
		build();
	}
	
	/**
	 * 构建
	 */
	private void build() {
		for (String filterWord : filterWords) {
			char[] words = filterWord.toCharArray();
			int length = words.length;
//			新增首字符索引
			firstWords[words[0]] = true;
			int len = endWords[words[length - 1]];
//			新增尾字符索引，记录其最长长度
			if (len < length) {
				endWords[words[length - 1]] = length;
			}
		}
	}

	public String find(String content) {
		List<String> list = doFind(content, false);
		if (list.size() == 0) {
			return null;
		}
		else {
			return list.get(0);
		}
	}
	
	public List<String> findAll(String content) {
		return doFind(content, true);
	}
	
	private List<String> doFind(String content, boolean all) {
		List<String> result = new ArrayList<String>();
		
		List<Integer> firstIndexs = new ArrayList<Integer>();
		char[] words = content.toCharArray();
		for (int i= 0; i < words.length; i++) {
//			判断是否有遇到首字符
			if (firstWords[words[i]]) {
				firstIndexs.add(i);
			}
			

			Integer length = endWords[words[i]];
//			并取得尾字符可能的最大长度，判断是否遇到尾字符，
			if (length != 0) {
				String word = "";
//				int count = 0;
//				取得首字符栈,判断是否有可能成为敏感词
				for(int j = firstIndexs.size() - 1; j >= 0; j--) {
					int index = firstIndexs.get(j);
					int len = i - index;
					if (len > length) {
//						超出最大长度,无法成为敏感词
						break;
					}
//					生成敏感词
//					count = len - count;
//					word = new String(words, index, count) + word;
					word = content.substring(index, i+1);
					if (filterWords.contains(word)) {
						logger.info("find filterword: " + word + " start: " + index + " end: " + i );
						result.add(word);
						if (!all) {
							return result;
						}
					}
				}
			}
		}
		return result;
	}
	
	public void clear() {
		if (filterWords != null) {
			filterWords.clear();
			filterWords = null;
		}
		firstWords = null;
		endWords = null;
	}
}
