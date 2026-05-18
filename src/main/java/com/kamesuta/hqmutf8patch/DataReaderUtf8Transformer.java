package com.kamesuta.hqmutf8patch;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public final class DataReaderUtf8Transformer implements IClassTransformer {
    private static final String TARGET = "hardcorequesting.network.DataReader";
    private static final String STRING_INTERNAL = "java/lang/String";
    private static final String UTF8_OWNER = "java/nio/charset/StandardCharsets";
    private static final String UTF8_DESC = "Ljava/nio/charset/Charset;";
    private static final String READ_STRING_DESC = "(Lhardcorequesting/network/DataBitHelper;)Ljava/lang/String;";

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (basicClass == null || !TARGET.equals(transformedName)) {
            return basicClass;
        }

        ClassReader reader = new ClassReader(basicClass);
        ClassNode node = new ClassNode();
        reader.accept(node, 0);

        boolean modified = false;
        for (MethodNode method : node.methods) {
            if (!"readString".equals(method.name) || !READ_STRING_DESC.equals(method.desc)) {
                continue;
            }

            for (AbstractInsnNode insn = method.instructions.getFirst(); insn != null; insn = insn.getNext()) {
                if (!(insn instanceof MethodInsnNode)) {
                    continue;
                }

                MethodInsnNode methodInsn = (MethodInsnNode) insn;
                if (methodInsn.getOpcode() == Opcodes.INVOKESPECIAL
                        && STRING_INTERNAL.equals(methodInsn.owner)
                        && "<init>".equals(methodInsn.name)
                        && "([B)V".equals(methodInsn.desc)) {
                    method.instructions.insertBefore(methodInsn,
                            new FieldInsnNode(Opcodes.GETSTATIC, UTF8_OWNER, "UTF_8", UTF8_DESC));
                    methodInsn.desc = "([BLjava/nio/charset/Charset;)V";
                    modified = true;
                    break;
                }
            }
        }

        if (!modified) {
            return basicClass;
        }

        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
        node.accept(writer);
        return writer.toByteArray();
    }
}
