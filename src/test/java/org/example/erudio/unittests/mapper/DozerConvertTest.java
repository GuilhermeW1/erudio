//package org.example.erudio.unittests.mapper;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import org.example.erudio.data.dto.mapper.DozerMapper;
//import org.example.erudio.data.dto.v1.Person2Dto;
//import org.example.erudio.model.Person;
//import org.example.erudio.unittests.mapper.moks.MockPerson;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//public class DozerConvertTest {
//    MockPerson inputObject;
//
//    @BeforeEach
//    public void setUp() {
//        inputObject = new MockPerson();
//    }
//
//    @Test
//    public void parseEntityToVOTest() {
//        Person2Dto output = DozerMapper.parseObject(inputObject.mockEntity(), Person2Dto.class);
//        assertEquals(Long.valueOf(0L), output.getKey());
//        assertEquals("First Name Test0", output.getFirstName());
//        assertEquals("Last Name Test0", output.getLastName());
//        assertEquals("Addres Test0", output.getAddress());
//        assertEquals("Male", output.getGender());
//    }
//
//    @Test
//    public void parseEntityListToVOListTest() {
//        List<Person2Dto> outputList = DozerMapper.parseListObjects(inputObject.mockEntityList(), Person2Dto.class);
//        Person2Dto outputZero = outputList.get(0);
//
//        assertEquals(Long.valueOf(0L), outputZero.getKey());
//        assertEquals("First Name Test0", outputZero.getFirstName());
//        assertEquals("Last Name Test0", outputZero.getLastName());
//        assertEquals("Addres Test0", outputZero.getAddress());
//        assertEquals("Male", outputZero.getGender());
//
//        Person2Dto outputSeven = outputList.get(7);
//
//        assertEquals(Long.valueOf(7L), outputSeven.getKey());
//        assertEquals("First Name Test7", outputSeven.getFirstName());
//        assertEquals("Last Name Test7", outputSeven.getLastName());
//        assertEquals("Addres Test7", outputSeven.getAddress());
//        assertEquals("Female", outputSeven.getGender());
//
//        Person2Dto outputTwelve = outputList.get(12);
//
//        assertEquals(Long.valueOf(12L), outputTwelve.getKey());
//        assertEquals("First Name Test12", outputTwelve.getFirstName());
//        assertEquals("Last Name Test12", outputTwelve.getLastName());
//        assertEquals("Addres Test12", outputTwelve.getAddress());
//        assertEquals("Male", outputTwelve.getGender());
//    }
//
//    @Test
//    public void parseVOToEntityTest() {
//        Person output = DozerMapper.parseObject(inputObject.mockDto(), Person.class);
//        assertEquals(Long.valueOf(0L), output.getId());
//        assertEquals("First Name Test0", output.getFirstName());
//        assertEquals("Last Name Test0", output.getLastName());
//        assertEquals("Addres Test0", output.getAddress());
//        assertEquals("Male", output.getGender());
//    }
//
//    @Test
//    public void parserVOListToEntityListTest() {
//        List<Person> outputList = DozerMapper.parseListObjects(inputObject.mockVOList(), Person.class);
//        Person outputZero = outputList.get(0);
//
//        assertEquals(Long.valueOf(0L), outputZero.getId());
//        assertEquals("First Name Test0", outputZero.getFirstName());
//        assertEquals("Last Name Test0", outputZero.getLastName());
//        assertEquals("Addres Test0", outputZero.getAddress());
//        assertEquals("Male", outputZero.getGender());
//
//        Person outputSeven = outputList.get(7);
//
//        assertEquals(Long.valueOf(7L), outputSeven.getId());
//        assertEquals("First Name Test7", outputSeven.getFirstName());
//        assertEquals("Last Name Test7", outputSeven.getLastName());
//        assertEquals("Addres Test7", outputSeven.getAddress());
//        assertEquals("Female", outputSeven.getGender());
//
//        Person outputTwelve = outputList.get(12);
//
//        assertEquals(Long.valueOf(12L), outputTwelve.getId());
//        assertEquals("First Name Test12", outputTwelve.getFirstName());
//        assertEquals("Last Name Test12", outputTwelve.getLastName());
//        assertEquals("Addres Test12", outputTwelve.getAddress());
//        assertEquals("Male", outputTwelve.getGender());
//    }
//}
