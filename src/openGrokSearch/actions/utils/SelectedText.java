package openGrokSearch.actions.utils;

import com.intellij.openapi.editor.Editor;

public class SelectedText {

    private final Editor editor;

    public SelectedText(Editor editor) {
        this.editor = editor;
    }

    public String getText() {
        final int start = this.editor.getSelectionModel().getSelectionStart();
        final int end = this.editor.getSelectionModel().getSelectionEnd();
        String text;
        CharSequence charSequence = this.editor.getDocument().getCharsSequence();

        if (start == end) {
            text = getWordAtCursor(
                charSequence,
                this.editor.getCaretModel().getOffset()
            );
        } else {
            text = charSequence.subSequence(start, end).toString();
        }

        return text;
    }

    private String getWordAtCursor(CharSequence editorText, int currentOffset) {
        if (editorText.length() == 0) return null;
        if (editorText.length() > 0 && !Character.isJavaIdentifierPart(editorText.charAt(currentOffset)) &&
                Character.isJavaIdentifierPart(editorText.charAt(currentOffset - 1))) {
            currentOffset--;
        }

        if (Character.isJavaIdentifierPart(editorText.charAt(currentOffset))) {
            int start = currentOffset;
            int end = currentOffset;

            while (start > 0 && Character.isJavaIdentifierPart(editorText.charAt(start - 1))) {
                start--;
            }

            while (end < editorText.length() && Character.isJavaIdentifierPart(editorText.charAt(end))) {
                end++;
            }

            return editorText.subSequence(start, end).toString();
        }

        return null;
    }
}
