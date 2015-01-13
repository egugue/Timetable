package htoyama.timetable.utils;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class StringUtilsTest {

    @Test
    public void trimSpacingは文字列の中に半角スペース全角スペースが入っていた場合トリムする()
            throws Exception{
        String target = " 　こんにちは　 ";
        String actual = StringUtils.trimSpacing(target);

        assertThat(actual, is("こんにちは"));
    }

    @Test
    public void trimSpacingは文字列の中に半角スペース全角スペースが入っていない場合そのままの文字列を返す()
            throws Exception{
        String target = "こんにちは";
        String actual = StringUtils.trimSpacing(target);

        assertThat(actual, is("こんにちは"));
    }

    @Test
    public void trimFileSchemeIfNeededは先頭にファイルスキームが付いている場合はトリムする()
            throws Exception {
        String target = "file:///hoge/fuga.txt";
        String actual = StringUtils.trimFileSchemeIfNeeded(target);

        assertThat(actual, is("/hoge/fuga.txt"));
    }

    @Test
    public void trimFileSchemeIfNeededは先頭にファイルスキームが付いていない場合はそのままの文字列を返す()
            throws Exception {
        String target = "/hoge/fuga.txt";
        String actual = StringUtils.trimFileSchemeIfNeeded(target);

        assertThat(actual, is("/hoge/fuga.txt"));
    }

}