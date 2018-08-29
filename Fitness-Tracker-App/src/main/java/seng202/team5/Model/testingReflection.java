package seng202.team5.Model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class testingReflection {


    public static void main(String[] args) {

        DataAnalyser data = new DataAnalyser();
        Class c = data.getClass();


        try {
            Method method = c.getDeclaredMethod("bean");
            method.invoke(c, null);
        } catch (NoSuchMethodException e) {
            System.out.println("beep");
        }  catch (IllegalAccessException e) {

        } catch (InvocationTargetException e) {

        }
    }

}
