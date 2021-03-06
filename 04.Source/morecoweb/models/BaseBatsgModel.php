<?php
namespace app\models;

use Yii;
use app\components\HDateTime;

/**
 * Model that has field $id, $data_status, $create_time, $create_user_id, $update_time, $update_user_id.
 */
class BaseBatsgModel extends BaseModel
{
  const DATA_STATUS_NEW = 1;
  const DATA_STATUS_UPDATE = 2;
  const DATA_STATUS_DELETE = 9;

  /**
   * Prepare data_status, update_time, create_time
   * attributes before performing validation.
   * @see CModel::beforeValidate()
   */
  public function beforeValidate()
  {
    $currentUserId = isset(Yii::$app->user) ? Yii::$app->user->id : NULL;
    if ($this->isNewRecord) {
      // data_status will be set when import data from backup csv etc.
      if (!$this->data_status) {
        $this->data_status = self::DATA_STATUS_NEW;
      }
      if (!$this->create_user_id) {
        $this->create_user_id = $currentUserId;
      }
      // create_time will be set when import data from backup csv etc.
      if (!$this->create_time) {
        $this->create_time = HDateTime::now()->toString();
      }
    } else {
      // Only set data_status to "update" if this is not deleted.
      if ($this->data_status == self::DATA_STATUS_NEW) {
        $this->data_status = self::DATA_STATUS_UPDATE;
      }
      if (!$this->update_user_id) {
        $this->update_user_id = $currentUserId;
      }
      $this->update_time = HDateTime::now()->toString();
    }
    return parent::beforeValidate();
  }

  /**
   * Perform massiveAssignment to a model.
   * @param CActiveRecord $model
   * @param array $parameters key=>value to assign to $model->attributes.
   * @param array $exclusiveFields Fields that are not assigned.
   */
//   public static function massiveAssign($model, $parameters,
//       $exclusiveFields = array('id', 'create_time', 'create_user_id', 'update_time', 'update_user_id'))
//   {
//     parent::massiveAssign($model, $parameters, $exclusiveFields);
//   }

  /**
   * Get only valid models (data_status <> deleted) from model list.
   * @param BaseBatsgModel[] $modelList
   */
//   public static function getValidModels($modelList)
//   {
//     $result = array();
//     foreach ($modelList as $model) {
//       if ($model->data_status <> self::DATA_STATUS_DELETE) {
//         $result[] = $model;
//       }
//     }
//     return $result;
//   }

  public static function findNotDeleted() {
    return self::find()->where(['!=', 'data_status', self::DATA_STATUS_DELETE]);
  }
  
  /**
   * Find all record that is not deleted logically (data_status <> 9).
   */
  public static function findAllNotDeleted()
  {
    return self::findNotDeleted()->all();
  }

  /**
   * Reset fields below to NULL.
   *   id
   *   data_status
   *   create_time
   *   create_user_id
   *   update_time
   *   update_user_id
   */
  public function resetCommonFields()
  {
    $this->setFieldToNull(array('id', 'data_status', 'create_time', 'create_user_id', 'update_time', 'update_user_id'));
  }

//   public function saveLogError()
//   {
//     $result = parent::save();
//     if (!$result) {
//       $this->logError();
//     }
//     return $result;
//   }

//   public function saveThrowError()
//   {
//     if (!$this->saveLogError()) {
//       throw new Exception("Error while saving " . $this->toString());
//     }
//   }

//   public function deleteLogically()
//   {
//     $this->data_status = self::DATA_STATUS_DELETE;
//     if (!$this->save()) {
//       $this->logError();
//       throw new Exception("Error while deleting " . $this);
//     }
//   }
}
?>