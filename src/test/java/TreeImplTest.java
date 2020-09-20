import com.momenton.orgchart.employee.Employee;
import com.momenton.orgchart.employee.TreeImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class TreeImplTest {

    private TreeImpl tree;

    @Before
    public void setup() {
        this.tree = new TreeImpl();
    }

    @Test
    public void testRoot() {
        this.tree.addChild(List.of("Alan","100",""));
        assertEquals(new Employee("Alan","100",""),this.tree.getRoot());
    }

    @Test
    public void testSampleData() {
        Employee node1 = this.tree.addChild(List.of("Alan","100","150"));
        Employee node2 =this.tree.addChild(List.of("Martin","220","100"));
        Employee node3 =this.tree.addChild(List.of("Jamie","150",""));
        Employee node4 =this.tree.addChild(List.of("Alex","275","100"));
        Employee node5 =this.tree.addChild(List.of("Steve","400","150"));
        Employee node6 =this.tree.addChild(List.of("David","190","400"));
        assertEquals(node3,this.tree.getRoot());
        assertEquals(Set.of(node1,node5),this.tree.getRoot().getChildren());
        assertEquals(Set.of(node2,node4),this.tree.getRoot().flattened().filter(n-> n.id().equals(node1.id())).findFirst().get().getChildren());
        assertEquals(Set.of(node6),this.tree.getRoot().flattened().filter(n-> n.id().equals(node5.id())).findFirst().get().getChildren());

    }

    @Test
    public void testEmployeeImmutability(){
        Employee node1 = this.tree.addChild(List.of("Alan","100","150"));
        Employee node2 = this.tree.addChild(List.of("Alan","100","160"));
        Employee node3 =this.tree.addChild(List.of("Jamie","150",""));
        Employee node4 =this.tree.addChild(List.of("Bill","160","150"));
        assertEquals(node3,this.tree.getRoot());
        assertEquals(Set.of(node1,node4),this.tree.getRoot().getChildren());
    }

    @Test
    public void testMultipleRoots(){
        Employee node1 = this.tree.addChild(List.of("Alan","100","150"));
        Employee node3 =this.tree.addChild(List.of("Jamie","150",""));
        Employee node4 =this.tree.addChild(List.of("Bill","160",""));
        assertEquals(node3,this.tree.getRoot());
        assertEquals(Set.of(node1),this.tree.getRoot().getChildren());
    }

    @Test
    public void testInvalidRecord(){
        Employee node1 = this.tree.addChild(List.of("Alan","100","150"));
        Employee node3 =this.tree.addChild(List.of("Jamie","150",""));
        Employee node4 =this.tree.addChild(List.of("Bill","160",""));
        assertEquals(node3,this.tree.getRoot());
        assertEquals(Set.of(node1),this.tree.getRoot().getChildren());
    }

}
