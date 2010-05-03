/*
Copyright (C) 2010 Christoph Gritschenberger

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/
package at.ac.tuwien.ifs.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Table {
	private List<List<String>> data = new ArrayList<List<String>>();

	public Table() {
	}

	public Table(Collection<List<String>> collection) {
		data.addAll(collection);
	}

	public void addLine(List<String> line) {
		data.add(line);
	}

	public void setCell(int x, int y, String cellData) {
		if (data.size() < x) {
			throw new RuntimeException("line not found");
		}
		List<String> line = data.get(x);
		if (line.size() < y) {
			throw new RuntimeException("column out of bounds");
		}
		line.set(y, cellData);
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		for (List<String> row : data) {
			result.append("|");
			for (String cell : row) {
				result.append(cell);
				result.append(" | ");
			}
			result.append("\n");
		}
		return result.toString();
	}

	/**
	 * 
	 * Indices start with 1
	 * 
	 * @param start
	 *            first column (incl)
	 * @param end
	 *            last column (incl)
	 * @return
	 */
	public List<List<String>> getCols(int start, int end) {
		List<List<String>> result = new ArrayList<List<String>>();
		if (start < 1 || end < start) {
			throw new RuntimeException(String.format("invalid range (%d-%d)",
					start, end));
		}
		for (List<String> row : data) {
			int realstart = start - 1;
			int realend = Math.min(end, row.size());
			List<String> copy = new ArrayList<String>();
			if (realend > realstart) {
				List<String> src = row.subList(realstart, realend);
				copy.addAll(src);
				int num = end - start + 1;
				while (copy.size() < num) {
					copy.add("");
				}
			}
			result.add(copy);
		}
		return result;
	}

	// @Override
	// public String toString() {
	// StringBuffer result = new StringBuffer();
	// List<Integer> lengths = getColSizes();
	// int i = 0;
	// for (List<String> row : data) {
	// for(String cellData : row){
	// StringBuffer cellBuf = new StringBuffer();
	// cellBuf.append(cellData);
	// fillToSize(result, lengths.get(i));
	// result.append("|");
	// i++;
	// }
	//			
	// }
	// return super.toString();
	// }
	//
	// private void fillToSize(StringBuffer result, int i) {
	// int diff = i - result.length();
	// for (i = 0; i < diff; i++) {
	// result.append(" ");
	// }
	// }
	//
	// private List<Integer> getColSizes() {
	// List<Integer> result = new ArrayList<Integer>();
	// for (List<String> row : data) {
	// for (int i = 0; i < row.size(); i++) {
	// while (result.size() < i) {
	// result.add(0);
	// }
	// int max = result.get(i);
	//
	// result.set(i, Math.max(max, row.size()));
	// }
	// }
	// return result;
	// }
}
