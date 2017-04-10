package com.lsj.trans;
import com.lsj.trans.Params.HttpGetParams;
import com.lsj.trans.Params.HttpParams;
import net.sf.json.JSONArray;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.FunctionObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.ast.FunctionCall;

public class GoogleDispatch extends Dispatch {


	static{
		GoogleDispatch instance = new GoogleDispatch();
		classMap.put("google", instance);
		classMap.put("Google", instance);
	}
	public GoogleDispatch(){

		langMap.put("en", "en");
		langMap.put("zh", "zh-CN");
	}
	
	@Override
	public String Trans(String from, String targ, String query) throws Exception{
		
		HttpParams params = new HttpGetParams()
				.put("client", "t")
				.put("sl", langMap.get(from))
				.put("tl", langMap.get(targ))
				.put("hl", "zh-CN")
				.put("dt", "at")
				.put("dt", "bd")
				.put("dt", "ex")
				.put("dt", "ld")
				.put("dt", "md")
				.put("dt", "qca")
				.put("dt", "rw")
				.put("dt", "rm")
				.put("dt", "ss")
				.put("dt", "t")
				
				.put("ie", "UTF-8")
				.put("oe", "UTF-8")
				.put("source", "btn")
				.put("srcrom", "1")
				.put("ssel", "0")
				.put("tsel", "0")
				.put("kc", "11")
				.put("tk", tk(query))
				.put("q", query);
		
		String jsonString = params.Send("http://translate.google.cn/translate_a/single");
		return ParseString(jsonString);
	}
	
	
	private String ParseString(String jsonString){
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		JSONArray segments = jsonArray.getJSONArray(0);
		StringBuilder result = new StringBuilder();
		
		for(int i=0; i<segments.size(); i++){
			result.append(segments.getJSONArray(i).getString(0));
		}
		
		return new String(result);
	}
	
	private String tk(String val) throws Exception{
		Context rhino = Context.enter();
		rhino.setOptimizationLevel(-1);
		String script ="function tk(a) {"
						+"var TKK = ((function() {var a = 561666268;var b = 1526272306;return 406398 + '.' + (a + b); })());\n"
						+"function b(a, b) { for (var d = 0; d < b.length - 2; d += 3) { var c = b.charAt(d + 2), c = 'a' <= c ? c.charCodeAt(0) - 87 : Number(c), c = '+' == b.charAt(d + 1) ? a >>> c : a << c; a = '+' == b.charAt(d) ? a + c & 4294967295 : a ^ c } return a }\n"
						+"for (var e = TKK.split('.'), h = Number(e[0]) || 0, g = [], d = 0, f = 0; f < a.length; f++) {"  
						+"var c = a.charCodeAt(f);"  
						+"128 > c ? g[d++] = c : (2048 > c ? g[d++] = c >> 6 | 192 : (55296 == (c & 64512) && f + 1 < a.length && 56320 == (a.charCodeAt(f + 1) & 64512) ? (c = 65536 + ((c & 1023) << 10) + (a.charCodeAt(++f) & 1023), g[d++] = c >> 18 | 240, g[d++] = c >> 12 & 63 | 128) : g[d++] = c >> 12 | 224, g[d++] = c >> 6 & 63 | 128), g[d++] = c & 63 | 128)"  
						+"}"      
						+"a = h;"  
						+"for (d = 0; d < g.length; d++) a += g[d], a = b(a, '+-a^+6');"  
						+"a = b(a, '+-3^+b+-f');"  
						+"a ^= Number(e[1]) || 0;"  
						+"0 > a && (a = (a & 2147483647) + 2147483648);"  
						+"a %= 1E6;"  
						+"return a.toString() + '.' + (a ^ h)\n"
						+"}";

		ScriptableObject scope = rhino.initStandardObjects();
		Scriptable that = rhino.newObject(scope);
		Function fct = rhino.compileFunction(scope, script, "script", 1, null);
		String result = fct.call(rhino,scope,that,new Object[]{val}).toString();

		Context.exit();
		return result;
	}
}