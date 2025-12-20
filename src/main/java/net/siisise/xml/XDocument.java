/*
 * Copyright 2025 okome.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.siisise.xml;

import org.w3c.dom.Document;

/**
 * XML Document の軽いwrap
 */
public class XDocument extends XNode<Document> {
    
    public XDocument(Document doc) {
        super(doc);
    }
    
    public XElement getDocumentElement() {
        return new XElement(val.getDocumentElement());
    }
    
    public XElement createElement(String tagName) {
        return new XElement(val.createElement(tagName));
    }
}
