package cn.feitu.javaparallel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

/**
 * Created by feitu on 17/6/24.
 */
public class ParallelStreamWithList {

    private List<Object> objects;

    private void init(){
        objects = new ArrayList<>();
        LongStream.range(0, 100).parallel().forEach(
                l  -> {
                    objects.add(new Object());
                }
        );
    }
    public static void main(String[] args) {
        ParallelStreamWithList psl = new ParallelStreamWithList();
        psl.init();
        System.out.println("objects size : "+psl.objects.size());
    }
}
