/* Copyright (c) 2003,2004, Stefan Haustein, Oberhausen, Rhld., Germany
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The  above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE. 
 *
 * Contributor(s): John D. Beatty, F. Hunter, Renaud Tognelli
 *
 * */


package org.ksoap2.serialization;

import java.util.Hashtable;


/** provides get and set methods for properties. Can be used to
    replace reflection (to some extend) for "serialization-aware"
    classes. Currently used in kSOAP and the RMS based kobjects object
    repository */



public interface KvmSerializable {


    /**
     * Returns the property at a specified index (for serialization)
     *
     * @param index the specified index 
     * @return the serialized property
     */
 
   Object getProperty (int index);

    /** returns the number of serializable properties */

    int getPropertyCount (); 



    /**
     * sets the property with the given index to the given value.
     * @param index the index to be set
     * @param value the value of the property
     */
    void setProperty (int index, Object value);


 
    /** 
     * Fills the given property info record. 
     * @param index the index to be queried 
     * @param properties  information about the (de)serializer
     * @param info  The return parameter, to be filled with information
     *    about the property with the given index. */
    
    void getPropertyInfo (int index, Hashtable properties, PropertyInfo info);

    
}
