package Apps;

import Exceptions.NoStudentsCExeption;
import Student.StudentMain;
import dataStructures.*;

public class SepChainHashTableStudyClass implements SepChainHashTableStudy{


    Map<String, List<StudentMain>> studentsByCountry;

    public SepChainHashTableStudyClass(Map<String, List<StudentMain>> studentsByCountry) {
        this.studentsByCountry = studentsByCountry;
    }


    @Override
    public void put(StudentMain s) {
        String country = s.getCountry();
        List<StudentMain> newList = studentsByCountry.get(country.toUpperCase());
        if (newList == null) {
            newList = new DoublyLinkedList<>();
            studentsByCountry.put(country.toUpperCase(), newList);
        }
        newList.addLast(s);
    }

    @Override
    public void remove(StudentMain s) {
        String key = s.getCountry().toUpperCase();
        List<StudentMain> list = studentsByCountry.get(key);
        if (list != null) {
            int idx = list.indexOf(s);
            if (idx != -1) {
                list.remove(idx);
                if (list.isEmpty()) {
                    studentsByCountry.remove(key);
                }
            }
        }
    }

    @Override
    public Iterator<StudentMain> iterator(String c) throws NoStudentsCExeption {
        List<StudentMain> list = studentsByCountry.get(c.toUpperCase());
        if (list == null) throw new NoStudentsCExeption();
        return list.iterator();
    }


}
