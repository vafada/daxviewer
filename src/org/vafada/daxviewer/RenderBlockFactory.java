package org.vafada.daxviewer;

public class RenderBlockFactory {
    public RenderBlock createBlock(DaxFileBlock block) {
        if (new EgaSpriteBlockSpecification().isSatisfiedBy(block)) {
            return new EgaSpriteBlock(block);
        }
        if (new EgaBlockSpecification().isSatisfiedBy(block)) {
            return new EgaBlock(block);
        }
        if (new MonoBlockSpecification().isSatisfiedBy(block)) {
            return new MonoBlock(block);
        }

        throw new IllegalArgumentException("Unknown block type");
    }
}
