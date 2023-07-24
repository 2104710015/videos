<template>
    <div>
        <p>
            <button  @click="list(1)" class="btn btn-lg  btn-primary">
                <i class="ace-icon fa fa-refresh"></i>
            </button>
             &nbsp;
            <!-- Button trigger modal -->
            <button @click="add()" class="btn btn-lg btn-success"   >
                <i class="ace-icon fa glyphicon-plus"></i>
             </button>
        </p>
        <table id="simple-table" class="table  table-bordered table-hover">
            <thead>
            <tr>
                <#list fieldList as field>
                    <#if field.nameHump!="createdAt" && field.nameHump!="updatedAt">
                    <th>${field.nameCn}</th>
                    </#if>
                </#list>
                <th>操作</th>
            </tr>
            </thead>

            <tbody>
            <tr v-for="${domain} in ${domain}s">
                <#list fieldList as field>
                    <#if field.nameHump!="createdAt" && field.nameHump!="updatedAt">
                        <#if field.enums>
                            <td>{{${field.enumsConst} | optionKV(${domain}.${field.nameHump})}}</td>
                        <#else>
                            <td>{{${domain}.${field.nameHump}}}</td>
                        </#if>
                   </#if>
                </#list>
                <td>
                    <div class="hidden-sm hidden-xs btn-group">

                        <button @click="update(${domain})" class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-pencil bigger-120"></i>
                        </button>

                        <button @click="del(${domain}.id)" class="btn btn-xs btn-danger">
                            <i class="ace-icon fa fa-trash-o bigger-120"></i>
                        </button>

                    </div>

                    <div class="hidden-md hidden-lg">
                        <div class="inline pos-rel">
                            <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                                <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                            </button>

                            <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">

                                <li>
                                    <a href="#"  @click="update(${domain})" class="tooltip-success" data-rel="tooltip" title="Edit">
                                                                    <span class="green">
                                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                                    </span>
                                    </a>
                                </li>

                                <li>
                                    <a href="#"  @click="del(${domain}.id)" class="tooltip-error" data-rel="tooltip" title="Delete">
                                                                    <span class="red">
                                                                        <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                                                    </span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
        <pagination ref="pagination" v-bind:list="list" v-bind:itemCount="8"></pagination>

        <!-- Modal -->
        <div class="modal fade" id="form-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">操作${tableNameCn}</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal">
                            <#list fieldList as field>
                                 <#if field.name!="id" && field.nameHump!="createdAt" && field.nameHump!="updatedAt">
                                <#if field.enums>
                                   <div class="form-group">
                                    <label class="col-sm-2 control-label">${field.nameCn}</label>
                                    <div class="col-sm-10">
                                      <select v-model="${domain}.${field.nameHump}" class="form-control">
                                        <option v-for="o in ${field.enumsConst}" v-bind:value="o.key">{{o.value}}</option>
                                      </select>
                                    </div>
                                  </div>
                                      <#else>
                                <div class="form-group">
                                    <label for="inputEmail1" class="col-sm-2 control-label">${field.nameCn}</label>
                                    <div class="col-sm-10">
                                        <input type="text" v-model="${domain}.${field.nameHump}"  class="form-control" id="inputEmail1" placeholder="请输入${field.nameCn}名称">
                                    </div>
                                </div>
                                 </#if>
                                 </#if>
                            </#list>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" @click="save()" class="btn btn-primary">确定</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import  Pagination from  "../../components/pagination";
    export default {
        components: {Pagination},
        name: '${domain}',
        data:function() {
            return {
                ${domain}:{},
                ${domain}s: [],
                <#list fieldList as field>
                <#if field.enums>
                ${field.enumsConst}: ${field.enumsConst},
                </#if>
                </#list>
            }
        },
        mounted: function () {
            let _this=this;
             _this.$refs.pagination.size=5;
            _this.list(1);
        },
        methods:{
            add(){
                let _this=this;
                _this.${domain}={};
                $("#form-modal").modal("show");
            },
            update(${domain}){
                let _this=this;
                _this.${domain}=$.extend({},${domain});
                $("#form-modal").modal("show");
            },
            del(id){
                  let _this=this;
                Confrim.Show("注意【${tableNameCn}删除】后不可恢复，请慎重!",function () {
                        _this.$ajax.delete(process.env.VUE_APP_SERVER + '/${module}/admin/${domain}/del/'+id).then((response)=>{
                            if (response.data.boo) {
                                _this.list(1)
                                prompt.success('${tableNameCn}删除成功!');
                            }else {
                                prompt.error(response.data.message);
                            }
                        })
                })
            },
            list(page){
                let _this=this;
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/${module}/admin/${domain}/list',{
                    page:page,
                    size: _this.$refs.pagination.size,
                }).then((response)=>{
                     _this.${domain}s=response.data.content.data;
                     _this.$refs.pagination.render(page,response.data.content.total);
                    Loading.hide();
                })
            },
            save(){
            let _this=this;
          /*      if(!Validator.require(_this.chapter.name,"名称")
                    ||!Validator.require(_this.chapter.courseId,"课程ID")
                    ||!Validator.length(_this.chapter.courseId,"课程ID",1,8 )){
                    return;
                }*/

                // 保存校验
                if (1 != 1
                    <#list fieldList as field>
                        <#if field.name!="id" && field.nameHump!="createdAt" && field.nameHump!="updatedAt" && field.nameHump!="sort">
                            <#if !field.nullAble>
                            || !Validator.require(_this.${domain}.${field.nameHump}, "${field.nameCn}")
                            </#if>
                            <#if (field.length > 0)>
                            || !Validator.length(_this.${domain}.${field.nameHump}, "${field.nameCn}", 1, ${field.length?c})
                            </#if>
                        </#if>
                    </#list>
                ) {
                    return;
                }
                Loading.show();
            _this.$ajax.post(process.env.VUE_APP_SERVER + '/${module}/admin/${domain}/saveAndUpdate',_this.${domain})
                .then((response)=>{
                    Loading.hide();
                    $("#form-modal").modal("hide");
                    if (response.data.boo) {
                      _this.list(1);
                        prompt.success('${tableNameCn}操作成功!');
                    }else {
                        prompt.error(response.data.message);
                    }
                })
            }

        }
    }
</script>
