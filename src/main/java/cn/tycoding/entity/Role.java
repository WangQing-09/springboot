package cn.tycoding.entity;

/**
 * @author wangqing
 * @date 2019-10-22
 */

public class Role {
    private Integer id;
    private String role;

    public Role() {
    }

    public Role(Integer id, String role) {
        this.id = id;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
