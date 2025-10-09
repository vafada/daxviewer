package org.vafada.daxviewer;

public class MonoBlockSpecification implements IFileBlockSpecification {
    @Override
    public boolean isSatisfiedBy(DaxFileBlock block) {
        return (block.data().length % 8) == 0 &&
                (block.getFilename().toUpperCase().startsWith("8X8"));
    }
}

