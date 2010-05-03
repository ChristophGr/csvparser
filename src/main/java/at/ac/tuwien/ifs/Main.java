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
package at.ac.tuwien.ifs;

import java.io.File;
import java.util.List;

import at.ac.tuwien.ifs.parser.Parser;
import at.ac.tuwien.ifs.parser.Table;


public class Main {

	private static final String FILENAME = "/home/profalbert/Downloads/QS_Bewertrung_Bsp2.csv";

	// private static final int[] MAX_POINTS = { 5, 5, 10, };

	public static void main(String[] args) {
		
		File file = new File(FILENAME);
		try {
			Table sheet = Parser.parse(file);
			List<List<String>> eval = sheet.getCols(1, 7);

			for (List<String> row : eval) {
				int points = 0;
				System.out.print("\"" + row.get(0) + "\",");
				System.out.print("\"2.1 - " + row.get(1) + " Punkte\n");
				System.out.print(row.get(2).replace("\"", "\"\"") + "\n");
				System.out.print("2.2 - " + row.get(3) + " Punkte\n");
				System.out.print(row.get(4).replace("\"", "\"\"") + "\n");
				System.out.print("2.3 - " + row.get(5) + " Punkte\n");
				System.out.print(row.get(6).replace("\"", "\"\"") + "\n");
				System.out.print("\",");

				points += Integer.parseInt(row.get(1));
				points += Integer.parseInt(row.get(3));
				points += Integer.parseInt(row.get(5));
				System.out.print(points + ",");
				System.out.println("");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
