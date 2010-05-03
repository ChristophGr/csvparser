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

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class Parser {
	private Reader reader;
	private ParserCore core;

	public class ParserCore {

		private static final char DEFAULT_TD = '\"';
		private static final char DEFAULT_FD = ',';
		private static final char DEFAULT_LD = '\n';

		protected char td = DEFAULT_TD;
		protected char fd = DEFAULT_FD;
		protected char ld = DEFAULT_LD;
		
		private StringBuffer cBuffer = new StringBuffer();
		private ArrayList<String> lBuffer = new ArrayList<String>();
		private ParseState state = new DefaultState(this);

		private Table ergebnis = new Table();
		
		protected void setState(ParseState state) {
			this.state = state;
		}

		protected void appendToCell(char c) {
			cBuffer.append(c);
		}

		protected void finishCell() {
			lBuffer.add(cBuffer.toString());
			cBuffer = new StringBuffer();
		}

		protected void finishLine() {
			if (cBuffer.length() > 0) {
				finishCell();
			}
			ergebnis.addLine(lBuffer);
			lBuffer = new ArrayList<String>();
		}

		protected void handleChar(char ch) {
			state.state(ch);
		}

		protected Table getErgebnis() {
			return ergebnis;
		}

	}
	
	protected Parser(File file) throws IOException {
		reader = new FileReader(file);
		core = new ParserCore();
	}

	private Parser(Reader reader) {
		core = new ParserCore();
		this.reader = reader;
	}

	public static Table parse(File file) throws IOException {
		Parser pars = new Parser(file);
		int c;
		while ((c = pars.reader.read()) != -1) {
			pars.core.handleChar((char) c);
		}
		return pars.core.getErgebnis();
	}

	public static Table parse(Reader reader) throws IOException {
		Parser p = new Parser(reader);
		int d;
		while ((d = p.reader.read()) != -1) {
			p.core.handleChar((char) d);
		}
		return p.core.getErgebnis();
	}
}
