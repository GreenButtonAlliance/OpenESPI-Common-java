/*
 *
 *    Copyright (c) 2018-2021 Green Button Alliance, Inc.
 *
 *    Portions (c) 2013-2018 EnergyOS.org
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */

package org.greenbuttonalliance.espi.common.utils;

import org.custommonkey.xmlunit.SimpleNamespaceContext;
import org.custommonkey.xmlunit.XMLUnit;

import java.util.HashMap;
import java.util.Map;

//@SuppressWarnings("rawtypes")
public class TestUtils {

	public static final String HTTP_WWW_W3_ORG_2005_ATOM = "http://www.w3.org/2005/Atom";
	public static final String HTTP_NAESB_ORG_ESPI = "http://naesb.org/espi";
	public static final String ATOM_PREFIX = "";
	public static final String ESPI_PREFIX = "espi";

	public static void setupXMLUnit() {

		XMLUnit.setXpathNamespaceContext(new SimpleNamespaceContext(
				namespaces()));
	}

	public static Map<String, String> namespaces() {
		Map<String, String> m = new HashMap<>();
		m.put(ATOM_PREFIX, HTTP_WWW_W3_ORG_2005_ATOM);
		m.put(ESPI_PREFIX, HTTP_NAESB_ORG_ESPI);
		return m;
	}

//	@Deprecated
//	public static void assertAnnotationPresent(Class clazz,
//			Class annotationClass) {
//		if (getClassAnnotation(clazz, annotationClass) == null)
//			throw new AssertionError(String.format(
//					"'%s' annotation is missing for class '%s'",
//					annotationClass.getCanonicalName(),
//					clazz.getCanonicalName()));
//	}

//	@Deprecated
//	public static void assertAnnotationPresent(Class clazz, String fieldName,
//			Class annotationClass) {
//		if (getAnnotation(clazz, fieldName, annotationClass) == null)
//			throw new AssertionError(String.format(
//					"'%s' annotation is missing for field '%s'",
//					annotationClass.getCanonicalName(), fieldName));
//	}

//	@Deprecated
//	public static void assertColumnAnnotation(Class clazz, String fieldName,
//			String columnName) {
//		Annotation annotation = assertAndGetAnnotation(clazz, fieldName,
//				Column.class);
//
//		String name = ((Column) annotation).name();
//		if (!name.equals(columnName))
//			throw new AssertionError(
//					String.format(
//							"Column name in @Column annotation not match expected '%s'",
//							name));
//	}

//	@Deprecated
//	public static void assertSizeValidation(Class clazz, String fieldName,
//			int min, int max) {
//		Annotation annotation = assertAndGetAnnotation(clazz, fieldName,
//				Size.class);
//
//		int minValue = ((javax.validation.constraints.Size) annotation).min();
//		if (min != minValue)
//			throw new AssertionError(String.format(
//					"@Size annotation minimum does not match expected '%s'",
//					min));
//
//		int maxValue = ((javax.validation.constraints.Size) annotation).max();
//		if (max != maxValue)
//			throw new AssertionError(String.format(
//					"@Size annotation maximum does not match expected '%s'",
//					max));
//	}

//	public static void assertXmlElement(Class clazz, String fieldName,
//			Class type) {
//		Annotation annotation = assertAndGetAnnotation(clazz, fieldName,
//				XmlElement.class);
//
//		Class annotationType = ((XmlElement) annotation).type();
//		if (type != annotationType)
//			throw new AssertionError(String.format(
//					"@XmlElement annotation type does not match expected '%s'",
//					type));
//	}

//	@Deprecated
//	private static Annotation assertAndGetAnnotation(Class clazz,
//			String fieldName, Class annotationClass) {
//		assertAnnotationPresent(clazz, fieldName, annotationClass);
//		return getAnnotation(clazz, fieldName, annotationClass);
//	}

//	@Deprecated
//	private static Annotation getAnnotation(Class clazz, String fieldName,
//			Class annotationClass) {
//
//		Field field;
//
//		try {
//			field = clazz.getDeclaredField(fieldName);
//		} catch (NoSuchFieldException e) {
//			throw new AssertionError(String.format(
//					"'%s' is missing field '%s'", clazz.getCanonicalName(),
//					fieldName));
//		}
//
//		for (Annotation annotation : field.getAnnotations()) {
//			if (annotation.annotationType().equals(annotationClass)) {
//				return annotation;
//			}
//		}
//
//		return null;
//	}

//	@Deprecated
//	private static Annotation getClassAnnotation(Class clazz,
//			Class annotationClass) {
//
//		for (Annotation annotation : clazz.getAnnotations()) {
//			if (annotation.annotationType().equals(annotationClass)) {
//				return annotation;
//			}
//		}
//
//		return null;
//	}

//	@Deprecated
//	public static void assertFieldNotMarshallable(Class clazz, String field) {
//		assertAnnotationPresent(clazz, field, XmlTransient.class);
//	}

//	@Deprecated
//	public static NamespaceContext getNamespaceContext() {
//		return new NamespaceContext() {
//			@Override
//			public String getNamespaceURI(String prefix) {
//				return namespaces().get(prefix);
//			}
//
//			@Override
//			public String getPrefix(String namespaceURI) {
//				return null;
//			}
//
//			@SuppressWarnings("rawtypes")
//			// TODO: Type this class strongly and hook back into the xml name
//			// world
//			@Override
//			public Iterator getPrefixes(String namespaceURI) {
//				return null;
//			}
//		};
//	}

//	@Deprecated
//	public static String getXPathValue(String expression, String xml)
//			throws XPathExpressionException {
//		String value;
//		XPathFactory xpathFactory = XPathFactory.newInstance();
//		XPath xpath = xpathFactory.newXPath();
//		xpath.setNamespaceContext(getNamespaceContext());
//
//		value = xpath.evaluate(expression,
//				new InputSource(new StringReader(xml))).trim();
//		return value;
//	}
}
