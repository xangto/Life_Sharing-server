package xangto.projects.life.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TreeUtils {
    /**
     * 通用树形构建工具
     *
     * @param list           扁平VO集合
     * @param idGetter       id函数
     * @param parentIdGetter parentId函数
     * @param childrenSetter children设置
     * @return 树形集合
     */
    public static <T> List<T> buildTree(List<T> list, Function<T, Long> idGetter, Function<T, Long> parentIdGetter, BiConsumer<T, List<T>> childrenSetter) {
        Map<Long, T> map = list.stream().collect(Collectors.toMap(idGetter, Function.identity()));
        List<T> root = new ArrayList<>();
        for (T item : list) {
            Long pid = parentIdGetter.apply(item);
            if (pid == null || pid.equals(0L)) {
                root.add(item);
            } else {
                T parent = map.get(pid);
                if (parent != null) {
                    List<T> children = new ArrayList<>();
                    List<T> exist = getChildren(parent, childrenSetter);
                    if (exist != null) {
                        children = exist;
                    }
                    children.add(item);
                    childrenSetter.accept(parent, children);
                }
            }
        }
        return root;
    }

    @SuppressWarnings("unchecked")
    private static <T> List<T> getChildren(T t, BiConsumer<T, List<T>> childrenSetter) {
        try {
            Field[] fields = t.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("children".equals(field.getName())) {
                    field.setAccessible(true);
                    return (List<T>) field.get(t);
                }
            }
        } catch (Exception e) {
        }
        return null;
    }
}
