/**
 * 
 */
package nc.ui.smp.pub;

import nc.ui.bd.ref.AbstractRefModel;
import nc.ui.pub.beans.UILabel;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.UITextField;

/**
 * 
* 类描述：构造界面控件元素   
* 创建人：LINQI
* 创建时间：2012-6-6 下午09:18:39   
* @version
 */
public class UIComponentFactory {

	private UIComponentFactory() {
		// TODO Auto-generated constructor stub
	}

	public static UILabel createUILabel(String name, String text) {
		UILabel rt = new UILabel(text);
		rt.setName(name);
		return rt;
	}
	
	public static UIRefPane createUIRefPane(String name, String refNodeName, AbstractRefModel model) {
		UIRefPane rt = new UIRefPane();
		rt.setName(name);
		rt.setRefNodeName(refNodeName);
		if (model != null) {
			rt.setRefModel(model);
		}
		return rt;
	}
	
	public static UITextField createUITextField(String text,boolean isEditable){
		
		UITextField field = new UITextField();
		field.setText(text);
		field.setEditable(isEditable);
		return field;
	}
	
}
