/*
 *    Copyright (c) 2018-2020 Green Button Alliance, Inc.
 *
 *    Portions copyright (c) 2013-2018 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.greenbuttonalliance.espi.common.domain;

import org.greenbuttonalliance.espi.common.utils.EspiMarshaller;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

public class ReadingTypeMarshallerTests {
	static final String XML_INPUT = "<ReadingType xmlns=\"http://naesb.org/espi\">"
			+ "<accumulationBehaviour>Behaviour</accumulationBehaviour>"
			+ "<commodity>Commodity</commodity>"
			+ "<dataQualifier>DataQualifier</dataQualifier>"
			+ "<flowDirection>FlowDirection</flowDirection>"
			+ "<intervalLength>1</intervalLength>"
			+ "<kind>Kind</kind>"
			+ "<phase>Phase</phase>"
			+ "<powerOfTenMultiplier>PowerOfTenMultiplier</powerOfTenMultiplier>"
			+ "<timeAttribute>TimeAttribute</timeAttribute>"
			+ "<uom>Uom</uom>"
			+ "<consumptionTier>ConsumptionTier</consumptionTier>"
			+ "<cpp>Cpp</cpp>"
			+ "<currency>Currency</currency>"
			+ "<interharmonic>"
			+ "<numerator>1</numerator>"
			+ "<denominator>2</denominator>"
			+ "</interharmonic>"
			+ "<measuringPeriod>MeasuringPeriod</measuringPeriod>"
			+ "<tou>Tou</tou>"
			+ "<defaultQuality>DefaultQuality</defaultQuality>"
			+ "<argument>"
			+ "<numerator>1</numerator>"
			+ "<denominator>3</denominator>"
			+ "</argument>" + "</ReadingType>";

	private ReadingType readingType;

	@Before
	public void before() throws JAXBException {
		readingType = EspiMarshaller.<ReadingType> unmarshal(XML_INPUT)
				.getValue();
	}

	@Test
	public void unmarshalsReadingType() {
		assertEquals(ReadingType.class, readingType.getClass());
	}

	@Test
	public void unmarshal_setsAccumulationBehaviour() {
		assertEquals("Behaviour", readingType.getAccumulationBehaviour());
	}

	@Test
	public void unmarshal_setsCommodity() {
		assertEquals("Commodity", readingType.getCommodity());
	}

	@Test
	public void unmarshal_setsDataQualifier() {
		assertEquals("DataQualifier", readingType.getDataQualifier());
	}

	@Test
	public void unmarshal_setsFlowDirection() {
		assertEquals("FlowDirection", readingType.getFlowDirection());
	}

	@Test
	public void unmarshal_setsIntervalLength() {
		assertEquals(new Long(1L), readingType.getIntervalLength());
	}

	@Test
	public void unmarshal_setsKind() {
		assertEquals("Kind", readingType.getKind());
	}

	@Test
	public void unmarshal_setsPhase() {
		assertEquals("Phase", readingType.getPhase());
	}

	@Test
	public void unmarshal_setsPowerOfTenMultiplier() {
		assertEquals("PowerOfTenMultiplier",
				readingType.getPowerOfTenMultiplier());
	}

	@Test
	public void unmarshal_setsTimeAttribute() {
		assertEquals("TimeAttribute", readingType.getTimeAttribute());
	}

	@Test
	public void unmarshal_setsUom() {
		assertEquals("Uom", readingType.getUom());
	}

	@Test
	public void unmarshal_setsConsumptionTier() {
		assertEquals("ConsumptionTier", readingType.getConsumptionTier());
	}

	@Test
	public void unmarshal_setsCpp() {
		assertEquals("Cpp", readingType.getCpp());
	}

	@Test
	public void unmarshal_setsCurrency() {
		assertEquals("Currency", readingType.getCurrency());
	}

	@Test
	public void unmarshal_setsInterharmonic() {
		ReadingInterharmonic interharmonic = new ReadingInterharmonic();
		interharmonic.setNumerator(new BigInteger("1"));
		interharmonic.setDenominator(new BigInteger("2"));

		assertEquals(interharmonic.getNumerator(), readingType
				.getInterharmonic().getNumerator());
		assertEquals(interharmonic.getDenominator(), readingType
				.getInterharmonic().getDenominator());
	}

	@Test
	public void unmarshal_setsMeasuringPeriod() {
		assertEquals("MeasuringPeriod", readingType.getMeasuringPeriod());
	}

	@Test
	public void unmarshal_setsTou() {
		assertEquals("Tou", readingType.getTou());
	}

	@Test
	public void unmarshal_setsDefaultQuality() {
		assertEquals("DefaultQuality", readingType.getDefaultQuality());
	}

	@Test
	public void unmarshal_setsArgument() {
		RationalNumber number = new RationalNumber();
		number.setNumerator(new BigInteger("1"));
		number.setDenominator(new BigInteger("3"));

		assertEquals(number.getNumerator(), readingType.getArgument()
				.getNumerator());
		assertEquals(number.getDenominator(), readingType.getArgument()
				.getDenominator());
	}

}
