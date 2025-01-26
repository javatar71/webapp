package ru.javatar;

import org.xbill.DNS.Lookup;
import org.xbill.DNS.Record;
import org.xbill.DNS.Type;
 

public class DnsCheck{
 
        public static boolean mxLookup(String email){
            try{
                String[] domain = email.split("@");
                try {
                    Record[] records = new Lookup(domain[1], Type.MX).run();
                    return records != null && records.length > 0;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return false;
                }
            }
            catch(Exception e){
                System.out.println(e.getMessage());
                return false;
            }

    }
}
