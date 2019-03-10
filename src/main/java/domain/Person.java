package domain;

import org.apache.ignite.cache.query.annotations.QuerySqlField;
import org.apache.ignite.cache.query.annotations.QueryTextField;

import java.io.Serializable;

/**
 * Created by lishutao on 2019/1/14.
 *
 * @author lishutao
 * @date 2019/1/14
 */
public class Person implements Serializable {
    /** Person ID (indexed). */
    @QuerySqlField(index = true)
    private long id;

    /** Organization ID (indexed). */
    @QuerySqlField(index = true)
    private long orgId;

    /** First name (not-indexed). */
    @QuerySqlField
    private String firstName;

    /** Last name (not indexed). */
    @QuerySqlField
    private String lastName;

    /** Resume text (create LUCENE-based TEXT index for this field). */
    @QueryTextField
    private String resume;

    /** Salary (indexed). */
    @QuerySqlField(index = true)
    private double salary;

}
