import date from './date'

export default {
  //从消息模板格式中解析填充字段数据
  parseFields(text) {
    const results = text.match(/{{(\S*?)}}/g) || [];
    let fields = [];
    results.forEach(result => {
      result = result.substring(2, result.indexOf('}}'));
      if (result && fields.indexOf(result) === -1) {
        fields.push(result);
      }
    });
    return fields;
  },
  //格式化消息预览内容
  formatTemplatePreview(templatePattern, fields, payload) {
    let previewMessage = templatePattern || '';
    if (fields && fields.length !== undefined && fields.length > 0) {
      fields.forEach(field => {
        if (payload[field]) {
          const reg = new RegExp(`{{${field}}}`, 'g');
          previewMessage = previewMessage.replace(reg, payload[field]);
        }
      });
    }
    return previewMessage;
  },
  //表格展开内容的格式化
  expandColumnFormat(record, column) {
    if (column.isDate) {
      return date(record[column.dataIndex]).format('YYYY-MM-DD HH:mm:ss');
    } else if (column.isList) {
      return record[column.dataIndex].join(', ');
    }
    return record[column.dataIndex];
  }
}