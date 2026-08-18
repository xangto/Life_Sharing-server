package xangto.projects.life.common;

import lombok.Data;

@Data
public class OptionVO {
    private String label;
    private String value;

    public OptionVO() {
    }

    public OptionVO(String label, String value) {
        this.label = label;
        this.value = value;
    }
}
