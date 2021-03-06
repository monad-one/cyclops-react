package cyclops.data.base;

import cyclops.control.Option;
import cyclops.data.base.HAMT.BitsetNode;
import cyclops.data.base.HAMT.Node;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class HAMTTest {
    Integer one = 1;
    Integer minusOne = -1;
    Integer two = 2;
    Integer thirtyOne = 31;
    @Test
    public void empty() throws Exception {
        Node<Integer, Integer> node = HAMT.<Integer, Integer>empty();
        assertThat(node.size(),equalTo(0));
    }
    @Test
    public void put1() throws Exception {
        Node<Integer, Integer> node = HAMT.<Integer, Integer>empty().plus(0,one.hashCode(),one,one);
        assertThat(node.get(0,one.hashCode(),one),equalTo(Option.some(1)));
    }
    @Test
    public void putMany() throws Exception {
        Node<Integer, Integer> node = HAMT.<Integer, Integer>empty();
        for(int i=0;i<1026;i++) {

            Integer next = i;
            node = node.plus(0, next.hashCode(), next, next);
            assertThat(node.toString(),node.get(0,next.hashCode(),next),equalTo(Option.some(next)));
            node = node.plus(0,minusOne.hashCode(),minusOne,minusOne);
        }
        System.out.println(node);
        node = node.plus(0,minusOne.hashCode(),minusOne,minusOne);
        assertTrue(node.get(0,minusOne.hashCode(),minusOne).isPresent());
        System.out.println(node);
    }
    @Test
    public void put31() throws Exception {
        Node<Integer, Integer> node = HAMT.<Integer, Integer>empty();
        for(int i=0;i<32;i++) {

            Integer next = i;
            if(i==31){
                System.out.println("31");
                System.out.println("Before : "+node);
                assertTrue(node.get(0,minusOne.hashCode(),minusOne).isPresent());
            }
            node = node.plus(0, next.hashCode(), next, next);
            if(i==31){
                System.out.println("After : "+node);
            }
            assertThat(node.toString(),node.get(0,next.hashCode(),next),equalTo(Option.some(next)));
            if(i==10)
                node = node.plus(0,minusOne.hashCode(),minusOne,minusOne);
        }
        System.out.println(node);
        node = node.plus(0,minusOne.hashCode(),minusOne,minusOne);
        assertTrue(node.get(0,thirtyOne.hashCode(),thirtyOne).isPresent());
        assertTrue(node.get(0,minusOne.hashCode(),minusOne).isPresent());
        System.out.println(node);
    }
    @Test
    public void put31DiffValues() throws Exception {
        Node<Integer, Integer> node = HAMT.<Integer, Integer>empty();
        for(int i=0;i<32;i++) {

            Integer next = i;
            if(i==31){
                System.out.println("31");
                System.out.println("Before : "+node);
                assertTrue(node.get(0,minusOne.hashCode(),minusOne).isPresent());
            }
            node = node.plus(0, next.hashCode(), next, next);
            node = node.plus(0, next.hashCode(), next+1, next+1);
            node = node.plus(0, next.hashCode(), next+2, next+2);
            node = node.plus(0, next.hashCode(), next+3, next+3);
            if(i==31){
                System.out.println("After : "+node);
            }
            assertThat(node.toString(),node.get(0,next.hashCode(),next),equalTo(Option.some(next)));
            if(i==10) {
                node = node.plus(0, minusOne.hashCode(), minusOne, minusOne);
                node = node.plus(0, minusOne.hashCode(), minusOne-1, minusOne-1);
                node = node.plus(0, minusOne.hashCode(), minusOne-2, minusOne-2);
            }
        }
        System.out.println(node);
        node = node.plus(0,minusOne.hashCode(),minusOne,minusOne);
        assertTrue(node.get(0,thirtyOne.hashCode(),thirtyOne).isPresent());
        assertTrue(node.get(0,minusOne.hashCode(),minusOne).isPresent());
        System.out.println(node);
    }

    @Test @Ignore
    public void problemBitsetNode(){
        Node<Integer, Integer>[] nodes = new Node[2];
        nodes[0] = new HAMT.ValueNode<>(-1,-1,-1);
        nodes[1] = new HAMT.ValueNode<>(31,31,31);
        BitsetNode<Integer,Integer> node = new BitsetNode<Integer,Integer>( new Long(Long.parseLong("10000000000000000000000000000001", 2)).intValue(),
                                            2,nodes);

        System.out.println("index "+  node.bitpos(thirtyOne.hashCode(), 0));
        System.out.println("index "+  node.bitpos(thirtyOne.hashCode(), 5));
        System.out.println("index "+  node.bitpos(thirtyOne.hashCode(), 10));

        System.out.println("index "+  node.bitpos(minusOne.hashCode(), 0));
        System.out.println("index "+  node.bitpos(minusOne.hashCode(), 5));
        System.out.println("index "+  node.bitpos(minusOne.hashCode(), 10));

       // assertTrue(node.get(10,minusOne.hashCode(),minusOne).isPresent());
        assertTrue(node.get(10,thirtyOne.hashCode(),thirtyOne).isPresent());
    }

    @Test
    public void replace() throws Exception {
        Node<Integer, Integer> node = HAMT.<Integer, Integer>empty().plus(0,1,1,1);
        assertThat(node.size(),equalTo(1));
        assertThat(node.get(0,1,1),equalTo(Option.some(1)));
        node = node.plus(0,1,1,2);
        assertThat(node.get(0,1,1),equalTo(Option.some(2)));
    }
    @Test
    public void replace12() throws Exception {
        Node<Integer, Integer> node = HAMT.<Integer, Integer>empty().plus(0,1,1,1)
                .plus(0,2,2,2)
                .plus(0,1,1,1)
                .plus(0,2,2,2);
        System.out.println(node);
        assertThat(node.size(),equalTo(2));

    }


}