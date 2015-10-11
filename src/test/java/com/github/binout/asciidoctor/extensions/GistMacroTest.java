/*
 * Copyright 2014 Benoît Prioux
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.binout.asciidoctor.extensions;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Options;
import org.asciidoctor.OptionsBuilder;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GistMacroTest {

    @Test
    public void should_understand_gist_block_macro() {
        Asciidoctor asciidoctor = Asciidoctor.Factory.create();
        asciidoctor.javaExtensionRegistry().blockMacro("gist", GistMacro.class);
        Options options = OptionsBuilder.options().backend("html5").get();

        String rendered = asciidoctor.convert("gist::5732518[]", options);

        assertThat(rendered).isEqualTo(
                "<div class=\"content\">\n" +
                        "<script src=\"https://gist.github.com/5732518.js\"></script>\n" +
                        "</div>");
    }
}
