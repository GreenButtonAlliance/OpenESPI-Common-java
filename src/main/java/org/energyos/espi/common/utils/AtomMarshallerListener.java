
package org.energyos.espi.common.utils;

import java.util.List;

import javax.xml.bind.Marshaller;
import javax.xml.datatype.XMLGregorianCalendar;

import org.energyos.espi.common.models.atom.ContentType;
import org.energyos.espi.common.models.atom.DateTimeType;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.models.atom.LinkType;

public class AtomMarshallerListener extends Marshaller.Listener {

    private String hrefFragment;
    private String entryFragment;
    private List<String> relRefList;
    
    long depth;

    public AtomMarshallerListener(String fragment) {
        this.hrefFragment = fragment;
	}

    public void setRelList(List<String> relRefList) {
    	this.relRefList = relRefList;
    }
    
    @Override
    public void beforeMarshal(Object source) {
        depth++;
        if (source instanceof EntryType) {
        	// set up the fragment for this particular entry.contents
        	ContentType content = ((EntryType)source).getContent();
        	this.entryFragment = content.buildSelfHref(hrefFragment);
        	
        }
    	if ((source instanceof LinkType) && (((LinkType) source).getRel().equals("self"))) {
    		((LinkType) source).setHref(mutateFragment((LinkType) source, this.entryFragment, 0));
    	}
    	if ((source instanceof LinkType) && (((LinkType) source).getRel().equals("up"))) {
    		
    		((LinkType) source).setHref(mutateFragment((LinkType) source, this.entryFragment, 1));
    	}
    	if ((source instanceof LinkType) && (((LinkType) source).getRel().equals("related"))) {
            if ((this.relRefList != null) && (!this.relRefList.isEmpty()) && (this.relRefList.get(0) != null)){
            	((LinkType) source).setHref(relRefList.remove(0));
            }
    	}
    	
    	if ((source instanceof DateTimeType)) {
    		// Normalize the calendar so it will print the "Z" correctly
            XMLGregorianCalendar xmlCal = ((DateTimeType)source).getValue();
            XMLGregorianCalendar xmlCal1 = xmlCal.normalize();
            ((DateTimeType)source).setValue(xmlCal1);
    	}

    }

    @Override
    public void afterMarshal(Object source) {
        depth--;
    }

    public String getHrefFragment() {
        return this.hrefFragment; 
    }
    
	// mutate the fragment based upon up/self/ref semantics
	//
	private String mutateFragment(Object source, String hrefFragment, Integer key) {
		String temp = hrefFragment;
		switch (key) {
		case 0: // a "self" reference - it should be fine
			break;
		case 1: // an "up" reference - make sure that if it has a "/id" on the tail, that it is dropped, otherwise its ""
			Integer i = temp.lastIndexOf("/");
            String t = temp.substring(i+1);
            if (t.matches("-?\\d+(\\.\\d+)?")) {
           	   temp = temp.substring(0, i);
            }
			break;
	    default:
	       // TODO for now, do nothing, but rel="ref" will be a problem
	    	break;
	    	
	}
		return temp;
		
	}
}