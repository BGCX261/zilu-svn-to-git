/**
 * 
 */
package com.zilu.util.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author dell
 *
 */
public class FileSelectorFactory {
	
	private class DefaultSelector extends FileSelector{
		public DefaultSelector(){
			super();
		}
		
		public DefaultSelector(int order){
			super(order);
		}
		
		public boolean isAvailable(File obj) {
			return true;
		}
		
	}
	
	private class TypeSelector extends FileSelector {
		private boolean type;
		public TypeSelector(boolean type) {
			super();
			this.type = type;
		}
		
		public boolean isAvailable(File obj) {
			boolean isFile = obj.isFile();
			return type? isFile: !isFile;
		}
		
	}
	
	private class NameSelector extends FileSelector {
		String name;
		public NameSelector(String name) {
			this.name = name;
		}
		
		public boolean isAvailable(File obj) {
			if (obj.getName().equals(name)) {
				return true;
			}
			else {
				return false;
			}
		}
	}
	
	private class NameLikedSelector extends FileSelector {
		String name;
		public NameLikedSelector(String name) {
			this.name = name;
		}
		
		public boolean isAvailable(File obj) {
			if (obj.getName().indexOf(name) != -1) {
				return true;
			}
			else {
				return false;
			}
		}
	}
	
	private class TypeNameSelector extends FileSelector {
		String name;
		public TypeNameSelector(String name) {
			this.name = name;
		}
		
		public boolean isAvailable(File obj) {
			if (obj.getName().endsWith(name)&& obj.isFile()) {
				return true;
			}
			else {
				return false;
			}
		}
	}
	
	private class RegexMatchSelector extends FileSelector {

		private Pattern pattern;
		
		public RegexMatchSelector(String regex) {
			pattern = Pattern.compile(regex);
		}
		
		public boolean isAvailable(File obj) {
			Matcher matcher = pattern.matcher(obj.getName());
			return matcher.matches();
		}
		
	}
	
	private class RegexFindSelector extends FileSelector {

		private Pattern pattern;
		
		public RegexFindSelector(String regex) {
			pattern = Pattern.compile(regex);
		}
		
		public boolean isAvailable(File obj) {
			Matcher matcher = pattern.matcher(obj.getName());
			return matcher.find();
		}
		
	}
	
	private class ComplexSelector extends FileSelector{
		List<FileSelector> selectors = new ArrayList<FileSelector>();
		public ComplexSelector() {
			super();
		}
		public ComplexSelector (int order) {
			super(order);
		}
		
		public ComplexSelector (List<FileSelector> selectors) {
			this.selectors = selectors;
		}
		
		public void addSelector(FileSelector selector) {
			selectors.add(selector);
		}
		
		public boolean isAvailable(File obj) {
			for (FileSelector selector : selectors) {
				if (!selector.isAvailable(obj)) {
					return false;
				}
			}
			return true;
		}
	}
	private static FileSelectorFactory instance;
	
	public static FileSelectorFactory getInstance() {
		if (instance == null) {
			instance = new FileSelectorFactory();
		}
		return instance;
	}
	public FileSelector createDefaultSelector() {
		return new DefaultSelector();
	}
	
	public FileSelector createDefaultSelector(int order) {
		return new DefaultSelector(order);
	}
	
	public FileSelector createTypeSelector(boolean type) {
		return new TypeSelector(type);
	}
	
	public FileSelector createNameSelector(String name) {
		return new NameSelector(name);
	}
	
	public FileSelector createNameLikedSelector(String name) {
		return new NameLikedSelector(name);
	}
	
	public FileSelector createTypeNameSelector(String name) {
		return new TypeNameSelector(name);
	}
	
	public FileSelector createRegexMatchSelector(String regex) {
		return new RegexMatchSelector(regex);
	}
	
	public FileSelector createRegexFindSelector(String regex) {
		return new RegexFindSelector(regex);
	}
	
	public FileSelector createComplexSelector() {
		return new ComplexSelector();
	}
	
	public FileSelector createComplexSelector(int order) {
		return new ComplexSelector(order);
	}
}
