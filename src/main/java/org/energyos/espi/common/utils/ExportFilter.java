package org.energyos.espi.common.utils;

import java.util.Map;

import org.energyos.espi.common.models.atom.DateTimeType;
import org.energyos.espi.common.models.atom.EntryType;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

@SuppressWarnings("restriction")
public class ExportFilter {
    private Map<String, String> params;
    private int matchedCounter = 0, emittedCounter = 0;

    public ExportFilter(Map<String, String> params) {
        this.params = params;
    }

    public boolean matches(EntryType entry) {

        if (hasParam("max-results")) {
        	if (!(params.get("max-results").equals("All"))) {
        		if(emittedCounter >= Integer.valueOf(params.get("max-results"))) {
        			 return false;
        		}
            }
        }
        if (hasParam("published-max")) {
        	if (!(params.get("published-max").equals("All"))) {
        		if (toTime("published-max") < toTime(entry.getPublished())) {
        			 return false;
            	}
            }
        }
        if (hasParam("published-min")) {
        	if (!(params.get("published-min").equals("All"))) {
        		if (toTime("published-min") > toTime(entry.getPublished())) {
                    return false;
        		}
        	}
        }

        if (hasParam("updated-max")) {
        	if (!(params.get("updated-max").equals("All"))) {
        		if (toTime("updated-max") < toTime(entry.getUpdated())) {
        	           return false;
        		}
        	}
        }

        if (hasParam("updated-min")) {
        	if (!(params.get("updated-min").equals("All"))) {
        		if (toTime("updated-min") > toTime(entry.getUpdated())) {
                    return false;
        		}
        	}
        }

        if (hasParam("start-index")) {
        	if (++matchedCounter < Integer.valueOf(params.get("start-index"))) {
                    return false;
        		
        	}
        }

        if (hasParam("depth")) {
        	if (!(params.get("depth").equals("All"))) {
        		if (emittedCounter > Integer.valueOf(params.get("depth"))) {
                    return false;
        		}
        	}
        }
        emittedCounter++;
        return true;
    }

    private boolean hasParam(String paramName) {
        return params.get(paramName) != null;
    }

    private long toTime(String key) {
        String param = params.get(key);

        return XMLGregorianCalendarImpl.parse(param).toGregorianCalendar().getTimeInMillis();
    }

    private long toTime(DateTimeType published) {
        return published.getValue().toGregorianCalendar().getTimeInMillis();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExportFilter that = (ExportFilter) o;

        return params.equals(that.params);

    }

    @Override
    public int hashCode() {
        return params.hashCode();
    }
}
