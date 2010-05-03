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

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import at.ac.tuwien.ifs.parser.Parser.ParserCore;

public abstract class ParseState {
	protected ParserCore parser;

	private static final Map<Class, ParseState> pool = new HashMap<Class, ParseState>();

	protected ParseState(ParserCore parser) {
		this.parser = parser;
	}

	protected abstract void state(char ch);

	protected ParseState getInstance(Class<? extends ParseState> stateClass) {
		ParseState result = pool.get(stateClass);
		if (result == null) {
			try {
				result = stateClass.getConstructor(ParserCore.class)
						.newInstance(parser);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				throw new Error(e.getStackTrace().toString());
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pool.put(stateClass, result);
		}
		return result;
	}

	protected void setState(Class<? extends ParseState> stateClass) {
		parser.setState(getInstance(stateClass));
	}
}
