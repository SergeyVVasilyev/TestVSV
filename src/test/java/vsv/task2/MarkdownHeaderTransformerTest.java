package vsv.task2;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MarkdownHeaderTransformerTest {

    @Test
    public void testEmpty() {
        assertNull(MarkdownHeaderTransformer.transform(null));
        assertEquals("", MarkdownHeaderTransformer.transform(""));
    }

    @Test
    public void testWithoutMarkdownHeader() {
        assertEquals("AAAA aaaa BBBBB", MarkdownHeaderTransformer.transform("AAAA aaaa BBBBB"));
    }

    @Test
    public void testTransforming() {
        assertEquals("<h1>Header will become</h1>", MarkdownHeaderTransformer.transform("# Header will become"));
        assertEquals("<h2>Header will become</h2>", MarkdownHeaderTransformer.transform("## Header will become"));
        assertEquals("<h3>Header will become</h3>", MarkdownHeaderTransformer.transform("### Header will become"));
        assertEquals("<h4>Header will become</h4>", MarkdownHeaderTransformer.transform("#### Header will become"));
        assertEquals("<h5>Header will become</h5>", MarkdownHeaderTransformer.transform("##### Header will become"));
        assertEquals("<h6>Header will become</h6>", MarkdownHeaderTransformer.transform("###### Header will become"));
    }

    @Test
    public void testTransformingWithSpace() {
        assertEquals("<h1>Header     will   become</h1>", MarkdownHeaderTransformer.transform("    # Header     will   become       "));
    }
}