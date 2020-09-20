package com.momenton.orgchart.employee;

import com.momenton.orgchart.input.ParseSequence;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class TreeImpl implements Tree<Employee> {
    private final static Logger LOGGER = Logger.getLogger(TreeImpl.class.getName());
    private Employee root;
    private int maxLeafNameLen;
    private int maxDepth;
    private static final String dummyRootId = "-1";
    private static final String dummyName = "";

    public TreeImpl(){
        init();
    }

    private void init() {
        root= new Employee(dummyName,dummyRootId,"");
        root.setLevel(-1);
    }

    @Override
    public void print() {
        this.root.flattened().forEach(n-> n.print(maxLeafNameLen, maxDepth));

    }
    @Override
    public Employee addChild(List<String> args) {
        Employee node =  buildNode(args);

        if(StringUtils.isEmpty(node.getManagerId())) {
            //set as root
            if(root.id().equals(dummyRootId)){
               root = node;
            } else {
                //throw a warning
                LOGGER.warning("Ignoring duplicate root record with id: "+ node.id());
                return node;
            }
        } else {
            findNodeById(node.getManagerId())
                    .ifPresentOrElse(
                            (manager) -> {
                                // parent node found
                                setMaxDepth(node, manager);
                                manager.addChildren(node);
                            },
                            ()-> {
                                //build dummy parent node and add the current node to its child list.
                                Employee parent = buildNode(List.of(dummyName,node.getManagerId(),dummyRootId));
                                setMaxDepth(parent,root);
                                setMaxDepth(node,parent);
                                parent.setProvisioned(false);
                                parent.addChildren(node);
                                root.addChildren(parent);
                            });


        }
        return node;

    }

    @Override
    public Employee getRoot() {
        return root;
    }

    private Employee buildNode(List<String> args) {
        Employee existingOrNew =  findNodeById(args.get(ParseSequence.ID.getValue()))
                .orElseGet(() -> new Employee(
                        args.get(ParseSequence.NAME.getValue()),
                        args.get(ParseSequence.ID.getValue()),
                        args.size() == 3 ? args.get(ParseSequence.MANAGER_ID.getValue()) : "")
                );
        if(!existingOrNew.isProvisioned()) {
            existingOrNew.setName(args.get(0));
            existingOrNew.setManagerId(args.size() == 3 ? args.get(2) : "");
            existingOrNew.setProvisioned(true);
            root.removeChild(existingOrNew);
        }
        maxLeafNameLen = Math.max(maxLeafNameLen, existingOrNew.name().length());
        return existingOrNew;
    }


    private void setMaxDepth(Employee child, Employee parent) {
        child.setLevel(parent.getLevel()+1);
        maxDepth = Math.max(maxDepth, child.getLevel());
    }


    private Optional<Employee> findNodeById(String id) {
        if(root.id().equals(id))
            return Optional.of(root);
        return root.flattened()
                .filter(node -> node.id().equals(id))
                .findFirst();

    }
}
