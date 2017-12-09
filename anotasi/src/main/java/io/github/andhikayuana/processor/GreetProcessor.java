package io.github.andhikayuana.processor;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.JavaFileObject;

@SupportedAnnotationTypes("io.github.andhikayuana.Greet")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class GreetProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        Collection<? extends Element> elementsAnnotatedWith =
                roundEnvironment.getElementsAnnotatedWith(Greet.class);

        List<TypeElement> typeElements = ElementFilter.typesIn(elementsAnnotatedWith);

        String packageName = null;
        String[] names = null;

        /**
         * 1. Grab package info of where a Greet annotation is written and args for the Greet annotation
         */
        for (TypeElement typeElement : typeElements) {
            PackageElement packageElement = (PackageElement) typeElement.getEnclosingElement();
            packageName = packageElement.getQualifiedName().toString();
            names = typeElement.getAnnotation(Greet.class).value();
        }

        if (packageName == null) return false;

        /**
         * 2. Write the Greeter Class
         */
        StringBuilder stringBuilder = new StringBuilder()
                .append("package " + packageName + ";\n\n")
                .append("public class Greeter {\n\n")
                .append("   public static String hello() {\n\n")
                .append("       return \"Hello Yuana ");

        /**
         * 3. append the Greeter annotation args in return statement
         */
        for (int i = 0; i < names.length; i++) {
            if (i == 0) stringBuilder.append(names[i]);
            else stringBuilder.append(", ").append(names[i]);
        }

        stringBuilder
                .append(" !\" ;\n")
                .append("   }\n")
                .append("}\n");


        /**
         * 4. write the Greeter class into a java source file
         */

        try {
            JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(packageName + ".Greeter");
            Writer writer = sourceFile.openWriter();
            writer.write(stringBuilder.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return false;
    }
}
