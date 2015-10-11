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

import org.asciidoctor.ast.AbstractBlock;
import org.asciidoctor.ast.Block;
import org.asciidoctor.extension.BlockMacroProcessor;

import java.util.Arrays;
import java.util.Map;

public class GistMacro extends BlockMacroProcessor {

    public GistMacro(String macroName, Map<String, Object> config) {
        super(macroName, config);
    }

    @Override
    public Block process(AbstractBlock parent, String target, Map<String, Object> attributes) {
        String content = "<div class=\"content\">\n" +
                "<script src=\"https://gist.github.com/"+target+".js\"></script>\n" +
                "</div>";
        return createBlock(parent, "pass", Arrays.asList(content), attributes, this.getConfig());
    }

}
