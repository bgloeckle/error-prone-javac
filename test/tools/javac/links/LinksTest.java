/*
 * Copyright (c) 2013, 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

/*
 * @test
 * @bug 4266026
 * @summary javac no longer follows symlinks
 * @library /tools/lib
 * @build ToolBox
 * @run main LinksTest
 */

import java.nio.file.Files;
import java.nio.file.Paths;

// Original test: test/tools/javac/links/links.sh
public class LinksTest {

    private static final String BSrc =
        "package a;\n" +
        "\n" +
        "public class B {}";

    private static final String TSrc =
        "class T extends a.B {}";

    public static void main(String... args) throws Exception {
        ToolBox tb = new ToolBox();
        tb.writeFile("tmp/B.java", BSrc);

        try {
            Files.createSymbolicLink(Paths.get("a"), Paths.get("tmp"));

            tb.new JavacTask()
                    .sourcepath(".")
                    .outdir(".")
                    .sources(TSrc)
                    .run();
        } catch (UnsupportedOperationException e) {
            System.err.println("Symbolic links not supported on this system. The test can't finish");
        }
    }

}
